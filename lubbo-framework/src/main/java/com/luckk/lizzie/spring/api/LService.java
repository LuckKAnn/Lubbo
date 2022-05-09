package com.luckk.lizzie.spring.api;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface LService {

//    也就是一个注解，所以可能可以设置以下的参数

    String version() default "1.0.0";


    String timeout() default  "3000";

    String group() default  "";



}
