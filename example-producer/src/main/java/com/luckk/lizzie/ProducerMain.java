package com.luckk.lizzie;

import com.luckk.lizzie.remoting.netty.NettyServer;
import com.luckk.lizzie.rpc.LubboScan;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @FileName: ProducerMain
 * @Author: LuckKun
 * @Email: 1546165200@qq.com
 * @Date: 2022/5/9 17:32
 */
//LK:为什么要加这个才能扫描到NettyServer
@LubboScan(scanPackage = "com.luckk.lizzie")
public class ProducerMain {

    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext cont = new AnnotationConfigApplicationContext(ProducerMain.class);
        NettyServer nettyServer =(NettyServer) cont.getBean("nettyServer");
        nettyServer.startConnection();

        HelloFunction bean = cont.getBean(HelloFunction.class);
        System.out.println(bean);


    }
}
