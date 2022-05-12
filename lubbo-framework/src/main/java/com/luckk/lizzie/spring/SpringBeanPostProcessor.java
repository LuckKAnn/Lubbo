package com.luckk.lizzie.spring;

import com.luckk.lizzie.registry.zk.ZookeeperRegistry;
import com.luckk.lizzie.rpc.Invocation;
import com.luckk.lizzie.rpc.proxy.RpcClientProxy;
import com.luckk.lizzie.spring.api.LService;
import com.luckk.lizzie.spring.api.LubboReference;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * @FileName: SpringBeanPostProcessor
 * @Author: LuckKun
 * @Email: 1546165200@qq.com
 * @Date: 2022/5/9 11:31
 */
@Component
public class SpringBeanPostProcessor implements BeanPostProcessor {


    /**
     * 处理Service
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

        if (bean.getClass().isAnnotationPresent(LService.class)){
            LService lService = bean.getClass().getAnnotation(LService.class);
            ServiceBean serviceBean = ServiceBean.builder().serviceRef(bean)
                    .version(lService.version())
                    .group(lService.group())
                    .timeout(lService.timeout()).build();
            ZookeeperRegistry.registryService(serviceBean);
        }

        return bean;
    }

    /**
     * 处理Reference，创建代理对象，
     * 注意，这里的写法为什么不一样，因为Reference是作用在属性上面的，而Service是注解在类上的，所以获取的方式应该不同
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        Field[] declaredFields = beanClass.getDeclaredFields();
        System.out.println("postProcessAfterInitialization");
        for (Field field : declaredFields){

            LubboReference reference = field.getAnnotation(LubboReference.class);

            if (reference==null) return bean;

            //真正应该保存的，是不是它的代理对象，相当于这里其实只是保存信息
            //将其所有的信息暂存到invocation当中，然后为其创建代理对象
            Invocation invocation = Invocation.builder()
                    .group(reference.group()).timeout(reference.timeout()).version(reference.version())
                    .build();
            RpcClientProxy proxy = new RpcClientProxy(invocation);
            //这里传入的不应该是bean的类吧，应该是这个属性的类

            //Object proxyObject = proxy.getProxy(bean.getClass());
            Object proxyObject = proxy.getProxy(field.getType());

            field.setAccessible(true);
        //    把代理对象注入进去
            try {
                field.set(bean,proxyObject);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }


        return bean;
    }
}
