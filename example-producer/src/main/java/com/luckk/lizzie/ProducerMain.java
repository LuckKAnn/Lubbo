package com.luckk.lizzie;

import com.luckk.lizzie.remoting.netty.NettyServer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @FileName: ProducerMain
 * @Author: LuckKun
 * @Email: 1546165200@qq.com
 * @Date: 2022/5/9 17:32
 */
public class ProducerMain {

    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext cont = new AnnotationConfigApplicationContext(ProducerMain.class);
        NettyServer nettyServer =(NettyServer) cont.getBean("nettyServer");
        nettyServer.startConnection();


    }
}
