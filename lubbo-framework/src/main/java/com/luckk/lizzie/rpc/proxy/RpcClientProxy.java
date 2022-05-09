package com.luckk.lizzie.rpc.proxy;

import com.luckk.lizzie.remoting.netty.NettyClient;
import com.luckk.lizzie.rpc.Invocation;
import com.luckk.lizzie.rpc.tansport.LubboRequest;
import com.luckk.lizzie.rpc.tansport.LubboResponse;
import org.apache.curator.framework.CuratorFramework;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * @FileName: RpcClientProxy
 * @Author: LuckKun
 * @Email: 1546165200@qq.com
 * @Date: 2022/5/9 11:51
 */
public class RpcClientProxy implements InvocationHandler {


    private Invocation invocation;

    public RpcClientProxy(Invocation invocation){
        this.invocation = invocation;
    }

    public  <T> T getProxy(final Class interfaceClass){
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                new Class[]{interfaceClass},this);
    }
    /**
     * 远程的服务器应该在什么时候启动呢？应该是Service启动的时候就启动了
     * 这个应该先去根据服务的名称，取到对应的服务目录，然后再去跟相应的服务器构建练剑
     * 这里服务目录应该是去netty上面再去查，这里并不需要
     *
     *
     * invoke应该做什么事？
     * 与远程服务器进行连接
     * 发送请求，接受请求，返回结果
     *
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        //这个类的类名就是接口的名称

        String name = proxy.getClass().getName();

        //创建Request对象,
        LubboRequest lubboRequest = new LubboRequest();

        lubboRequest.setRequestId(UUID.randomUUID().toString());
        lubboRequest.setClassName(name);
        lubboRequest.setMethodName(method.getName());
        lubboRequest.setMethodParams(method.getParameterTypes());
        lubboRequest.setParamsVal(args);

        NettyClient nettyClient = new NettyClient();
        nettyClient.RpcCall(lubboRequest);
        //起码要完成参数的封装吧，还要把这个对象传递给netty，问题是这个对象的参数信息如何拿到
        CompletableFuture<LubboResponse>requestAns = nettyClient.getRequestAns(lubboRequest.getRequestId());
        String ans = requestAns.get().getRpcResult();

        return ans;
    }
}
