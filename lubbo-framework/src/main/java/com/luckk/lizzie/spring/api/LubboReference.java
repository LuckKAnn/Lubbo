package com.luckk.lizzie.spring.api;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface LubboReference {

    String group() default  "";

    String version() default "1.0.0";


    String timeout() default "3000";
}
