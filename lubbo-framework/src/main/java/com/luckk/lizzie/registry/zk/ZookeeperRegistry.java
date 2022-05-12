package com.luckk.lizzie.registry.zk;

import com.luckk.lizzie.registry.ResourcesURL;
import com.luckk.lizzie.spring.ServiceBean;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;

import java.net.InetAddress;

/**
 * @FileName: ZookeeperRegistry
 * @Author: LuckKun
 * @Email: 1546165200@qq.com
 * @Date: 2022/5/9 11:37
 */
@Slf4j
public class ZookeeperRegistry {

    /**
     * 服务注册的时候，服务应该是要拿到它实现的接口的，这怎么去拿呢
     *
     * @param serviceBean
     */
    public static void registryService(ServiceBean serviceBean){
        try {
            //TODO 为什么这里拿到的网卡地址不对
            //String hostAddress = InetAddress.getLocalHost().getHostAddress();
            String hostAddress = "127.0.0.1";
            log.info("registry service Address"+hostAddress);
            ResourcesURL resourcesURL =new ResourcesURL();
            resourcesURL.setVal(hostAddress);
            CuratorFramework connection = ZookeeperClient.getConnection();
            ZookeeperUtils.createNodes(connection,resourcesURL,serviceBean);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
