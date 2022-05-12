package com.luckk.lizzie.rpc;

import com.luckk.lizzie.rpc.tansport.LubboRequest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * @FileName: Invoker
 * @Author: LuckKun
 * @Email: 1546165200@qq.com
 * @Date: 2022/5/9 17:14
 */
public class Invoker implements Callable<String> {

    LubboRequest request;

    AnnotationConfigApplicationContext springContext;
    public Invoker(LubboRequest lubboRequest, AnnotationConfigApplicationContext springContext){
        this.request  = lubboRequest;
        this.springContext = springContext;
    }

    @Override
    public String call() throws Exception {

        String requestId = request.getRequestId();
        String className = request.getClassName();
        Class<?> targetClass = null;
        Method method = null;
        try {
            //LK 这是一个接口，不应该通过他去创建实例然后执行
            //应该找到他的实现类，如何找到他的实现类呢？通过Spring？
             targetClass = Class.forName(className);
            Object bean = springContext.getBean(targetClass);
            //Object instance = Class.forName(className).newInstance();
             method = bean.getClass().getMethod(request.getMethodName(), request.getMethodParams());
            //method = targetClass.getMethod(request.getMethodName(),request.getMethodParams());
            Object invoke = method.invoke(bean, request.getParamsVal());
            return (String) invoke;
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
