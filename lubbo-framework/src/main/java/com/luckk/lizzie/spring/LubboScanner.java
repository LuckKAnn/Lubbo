package com.luckk.lizzie.spring;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.lang.annotation.Annotation;

/**
 * @FileName: LubboScanner
 * @Author: LuckKun
 * @Email: 1546165200@qq.com
 * @Date: 2022/5/12 15:26
 */
public class LubboScanner extends ClassPathBeanDefinitionScanner {
    public LubboScanner(BeanDefinitionRegistry registry, Class<? extends Annotation> annoType) {
        super(registry);
        super.addIncludeFilter(new AnnotationTypeFilter(annoType));
    }


    @Override
    public int scan(String... basePackages) {
        return super.scan(basePackages);
    }
}
