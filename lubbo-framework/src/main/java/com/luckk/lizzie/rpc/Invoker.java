package com.luckk.lizzie.rpc;

import com.luckk.lizzie.rpc.tansport.LubboRequest;

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

    public Invoker(LubboRequest lubboRequest){
        this.request  = lubboRequest;
    }

    @Override
    public String call() throws Exception {

        String requestId = request.getRequestId();
        String className = request.getClassName();
        Class<?> targetClass = null;
        Method method = null;
        try {
            targetClass = Class.forName(className);
            method = targetClass.getMethod(request.getMethodName(),request.getMethodParams());
            Object invoke = method.invoke(targetClass, request.getParamsVal());
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
