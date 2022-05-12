package com.luckk.lizzie;

import com.luckk.lizzie.controller.HelloController;
import com.luckk.lizzie.remoting.netty.NettyServer;
import com.luckk.lizzie.rpc.LubboScan;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @FileName: ConsumerMain
 * @Author: LuckKun
 * @Email: 1546165200@qq.com
 * @Date: 2022/5/9 17:32
 */
@LubboScan(scanPackage = "com.luckk.lizzie")
public class ConsumerMain {

    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext cont = new AnnotationConfigApplicationContext(ConsumerMain.class);
        HelloController helloController = (HelloController) cont.getBean("helloController");
        helloController.rpc();
    }
}
