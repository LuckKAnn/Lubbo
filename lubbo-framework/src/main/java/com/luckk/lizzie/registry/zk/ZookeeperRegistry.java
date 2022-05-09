package com.luckk.lizzie.registry.zk;

import com.luckk.lizzie.registry.ResourcesURL;
import com.luckk.lizzie.spring.ServiceBean;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.apache.curator.framework.CuratorFramework;

import java.net.InetAddress;

/**
 * @FileName: ZookeeperRegistry
 * @Author: LuckKun
 * @Email: 1546165200@qq.com
 * @Date: 2022/5/9 11:37
 */
@Log4j
public class ZookeeperRegistry {

    /**
     * 服务注册的时候，服务应该是要拿到它实现的接口的，这怎么去拿呢
     *
     * @param serviceBean
     */
    public static void registryService(ServiceBean serviceBean){
        try {
            String hostAddress = InetAddress.getLocalHost().getHostAddress();
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
