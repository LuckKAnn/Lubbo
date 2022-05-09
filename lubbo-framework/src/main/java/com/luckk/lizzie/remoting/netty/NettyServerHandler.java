package com.luckk.lizzie.remoting.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @FileName: NettyServerHandler
 * @Author: LuckKun
 * @Email: 1546165200@qq.com
 * @Date: 2022/4/12 19:28
 */
//TODO:这两个有什么区别
public class NettyServerHandler extends SimpleChannelInboundHandler {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        System.out.println("");
    }
}
