package com.luckk.lizzie.remoting.netty;

import com.luckk.lizzie.rpc.tansport.LubboRequest;
import com.luckk.lizzie.rpc.tansport.LubboResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @FileName: NettyClientHandler
 * @Author: LuckKun
 * @Email: 1546165200@qq.com
 * @Date: 2022/4/12 20:03
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    private ChannelHandlerContext context;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        context = ctx;
    }

    /**
     * 难不成收到消息之后，还需要转换成Response，然后拿到id，然后再放回去吗
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if (msg instanceof LubboResponse){
            LubboResponse lubboResponse = (LubboResponse) msg;
            NettyClient.completeRPC(lubboResponse);
        }
    }

    public synchronized void call() throws Exception {
        context.writeAndFlush("info");
    }

    public synchronized void call(LubboRequest lubboRequest) throws Exception {
        context.writeAndFlush(lubboRequest);
    }
    public synchronized void call(String info) throws Exception {
        context.writeAndFlush(info);
    }
}
