package com.luckk.lizzie.service;

import com.luckk.lizzie.HelloFunction;
import com.luckk.lizzie.spring.api.LService;

/**
 * @FileName: HelloService
 * @Author: LuckKun
 * @Email: 1546165200@qq.com
 * @Date: 2022/5/9 17:29
 */
@LService
public class HelloService implements HelloFunction {

    @Override
    public String sayHello() {
        return "helloService say hello to you lubbo";
    }
}
