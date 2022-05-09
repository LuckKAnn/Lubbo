package com.luckk.lizzie.remoting.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @FileName: NettyServer
 * @Author: LuckKun
 * @Email: 1546165200@qq.com
 * @Date: 2022/4/12 19:22
 */
@Slf4j
@Component("nettyServer")
public class NettyServer {

    public  NettyServerHandle nettyServerHandle = null;
    public  void  startConnection() throws InterruptedException {

         nettyServerHandle = new NettyServerHandle();
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,1024)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("decoder", new ObjectDecoder(ClassResolvers
                                    .weakCachingConcurrentResolver(this.getClass()
                                            .getClassLoader())));
                            ch.pipeline().addLast("encoder", new ObjectEncoder());
                                 ch.pipeline().addLast(nettyServerHandle);
                        }});
            System.out.println("netty start");
            log.info("netty server start ");
            ChannelFuture chf = bootstrap.bind(9000).sync();
            log.info("netty server stat end.....");
            chf.channel().closeFuture().sync();
        }
        catch (Exception e){
            log.warn("netty server start fail....");
        }
        finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
