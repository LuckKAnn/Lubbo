package com.luckk.lizzie.spring;

import com.luckk.lizzie.rpc.LubboScan;
import com.luckk.lizzie.spring.api.LService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.StandardAnnotationMetadata;
import org.springframework.stereotype.Component;

/**
 * @FileName: LubboScanRegistry
 * @Author: LuckKun
 * @Email: 1546165200@qq.com
 * @Date: 2022/5/12 15:23
 */
@Slf4j
public class LubboScanRegistry implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {


    public static final String BASE_PACKAGE = "com.luckk.lizzie";


    public static final String BASE_PACKAGE_ATTRIBUTE_NAME = "scanPackage";
    ResourceLoader resourceLoader;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {

        this.resourceLoader = resourceLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        System.out.println("registerBeanDefinitions");
        /**
         * 先去找到lubboScan注解内部配置的basepackage注解
         */
        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(LubboScan.class.getName()));
        String[] rpcScanBasePackages = new String[0];
        if (annotationAttributes!=null){
        //    说明有使用这个注解
            rpcScanBasePackages= annotationAttributes.getStringArray(BASE_PACKAGE_ATTRIBUTE_NAME);

        }


        if (rpcScanBasePackages.length==0){
            //这一行代码是在获取注解标注的类路径？
            rpcScanBasePackages = new String[]{((StandardAnnotationMetadata) annotationMetadata).getIntrospectedClass().getPackage().getName()};
        }



        LubboScanner lubboScanner = new LubboScanner(beanDefinitionRegistry,LService.class);
        LubboScanner componentScanner = new LubboScanner(beanDefinitionRegistry, Component.class);


        if (resourceLoader!=null){
            lubboScanner.setResourceLoader(resourceLoader);
            componentScanner.setResourceLoader(resourceLoader);
        }


        int lubboRpcScanNum = lubboScanner.scan(rpcScanBasePackages);
        log.info("rpcServiceScanner扫描的数量 [{}]", lubboRpcScanNum);
        int springBeanAmount = componentScanner.scan(BASE_PACKAGE);
        log.info("springBeanScanner扫描的数量 [{}]", springBeanAmount);

    }


}
