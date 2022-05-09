package com.luckk.lizzie.controller;

import com.luckk.lizzie.HelloFunction;
import com.luckk.lizzie.spring.api.LubboReference;
import org.springframework.stereotype.Component;

/**
 * @FileName: HelloController
 * @Author: LuckKun
 * @Email: 1546165200@qq.com
 * @Date: 2022/5/9 17:31
 */
@Component("helloController")
public class HelloController {

    @LubboReference
    public HelloFunction helloFunction;


    public void  rpc(){
        String s = helloFunction.sayHello();
        System.out.println(s);
    }
}
