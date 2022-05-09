package com.luckk.lizzie.registry.zk;

import com.luckk.lizzie.registry.ResourcesURL;
import com.luckk.lizzie.spring.ServiceBean;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.GetDataBuilder;
import org.apache.zookeeper.CreateMode;

import java.nio.charset.StandardCharsets;

/**
 * @FileName: ZookeeperUtils
 * @Author: LuckKun
 * @Email: 1546165200@qq.com
 * @Date: 2022/5/9 11:38
 */
public class ZookeeperUtils {


    private  static  final String TAG = "LUBOO";

    private static final String DIRECTORY  ="/lubbo";

    public static void createNodes(CuratorFramework client, ResourcesURL url, ServiceBean serviceBean) throws Exception {

        client.create().withMode(CreateMode.EPHEMERAL).forPath(DIRECTORY+serviceBean.getServiceName(), url.getVal().getBytes());
    }


    public static String getNodes(CuratorFramework client, String serviceName) throws Exception {
        //TODO:如果有多个地址怎么办呢？

        GetDataBuilder data = client.getData();
        byte[] bytes = data.forPath(DIRECTORY + serviceName);
        return new String(bytes, StandardCharsets.UTF_8);
    }

}
