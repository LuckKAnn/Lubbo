package com.luckk.lizzie.rpc;


import com.luckk.lizzie.spring.LubboScanRegistry;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(LubboScanRegistry.class)
public @interface LubboScan {



    String [] scanPackage();
}
