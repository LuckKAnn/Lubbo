package com.luckk.lizzie;

import com.luckk.lizzie.controller.HelloController;
import com.luckk.lizzie.remoting.netty.NettyServer;
import com.luckk.lizzie.rpc.LubboScan;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @FileName: ConsumerMain
 * @Author: LuckKun
 * @Email: 1546165200@qq.com
 * @Date: 2022/5/9 17:32
 */
@LubboScan(scanPackage = "com.luckk.lizzie")
public class ConsumerMain {

    private static ScheduledExecutorService service = Executors.newScheduledThreadPool(10);


    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext cont = new AnnotationConfigApplicationContext(ConsumerMain.class);
        HelloController helloController = (HelloController) cont.getBean("helloController");
        helloController.rpc();
        //5s执行一次
        long period = 1L;
        service.scheduleAtFixedRate(()->{
            HelloController controller = (HelloController)cont.getBean("helloController");
            controller.rpc();
        },0,period, TimeUnit.SECONDS);

    }
}
