package com.luckk.lizzie.registry.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;

/**
 * @FileName: ZookeeperClient
 * @Author: LuckKun
 * @Email: 1546165200@qq.com
 * @Date: 2022/5/9 11:37
 */
public class ZookeeperClient {

    public static CuratorFramework getConnection() throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString("152.136.30.108:2181")
                .sessionTimeoutMs(5000).retryPolicy(new RetryOneTime(3000))
                .namespace("create").build();

        client.start();
        client.blockUntilConnected();
        //client.create().withMode(CreateMode.EPHEMERAL).forPath("/node1","lkk".getBytes());

        return client;
    }
}
