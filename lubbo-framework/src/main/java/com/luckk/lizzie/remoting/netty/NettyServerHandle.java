package com.luckk.lizzie.remoting.netty;

import com.luckk.lizzie.rpc.Invoker;
import com.luckk.lizzie.rpc.tansport.LubboRequest;
import com.luckk.lizzie.rpc.tansport.LubboResponse;
import com.sun.org.apache.regexp.internal.RE;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @FileName: NettyServerHandle
 * @Author: LuckKun
 * @Email: 1546165200@qq.com
 * @Date: 2022/4/12 19:30
 */
public class NettyServerHandle extends ChannelInboundHandlerAdapter  {

    public  String msg ;

    public ChannelHandlerContext ctx;


    private ExecutorService executorService = new ThreadPoolExecutor(10, 10, 1, TimeUnit.MINUTES,
            new LinkedBlockingDeque<>(100), new ThreadFactory() {
        AtomicInteger id = new AtomicInteger();

        @Override
        public Thread newThread(Runnable r) {
            String threadName = "miniDubbo_server_biz_thread_" + id.incrementAndGet();
            return new Thread(r, threadName);
        }
    });
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        /**
         * 找到对应的实现类，调用方法返回结果
         */
        this.ctx = ctx;
        if (msg instanceof LubboRequest){
            LubboRequest request = (LubboRequest) msg;
            String requestId = request.getRequestId();
            String className = request.getClassName();
        //    有了类的名称，应该去找这个类的实现类
        //    直接去New一个吗
            Future<String> future = executorService.submit(new Invoker(request));
            String ans = future.get();
            LubboResponse lubboResponse = LubboResponse.builder()
                            .className(className).requestId(requestId).rpcResult(ans).build();
            ctx.writeAndFlush(lubboResponse);
        }
    }



    public void processRequest(ChannelHandlerContext ctx ,Object msg) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException {
        System.out.println("获取到需要调用的方法是:"+(String)msg);
        String methodName = (String)msg;

        Class<?> aClass = Class.forName(methodName);
        Method[] declaredMethod = aClass.getDeclaredMethods();
        Object invoke =null;
        for (Method method :declaredMethod){
            if (method.getName().equals("sayHello")){
                 invoke = method.invoke(aClass.newInstance());
            }
        }
        ctx.writeAndFlush((String)invoke);
    }

}
