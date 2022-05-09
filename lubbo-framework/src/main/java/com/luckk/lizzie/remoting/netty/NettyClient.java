package com.luckk.lizzie.remoting.netty;

import com.luckk.lizzie.registry.zk.ZookeeperClient;
import com.luckk.lizzie.registry.zk.ZookeeperUtils;
import com.luckk.lizzie.rpc.exception.LubboException;
import com.luckk.lizzie.rpc.tansport.LubboRequest;
import com.luckk.lizzie.rpc.tansport.LubboResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @FileName: NettyServer
 * @Author: LuckKun
 * @Email: 1546165200@qq.com
 * @Date: 2022/4/12 19:22
 */
@Slf4j
public class NettyClient {


    private static ConcurrentHashMap<String,NettyClientHandler> cacheConnections = new ConcurrentHashMap<>();


    private static ConcurrentHashMap<String,CompletableFuture<LubboResponse>> cacheRequest = new ConcurrentHashMap<>();

    public CompletableFuture<LubboResponse> getRequestAns(String requestId){
        return cacheRequest.get(requestId);
    }



    public static void completeRPC(LubboResponse response){
        //TODO:这里要不要加锁呢？如果报异常了呢？
        String requestId = response.getRequestId();
        CompletableFuture<LubboResponse> lubboResponseCompletableFuture = cacheRequest.get(requestId);
        if (lubboResponseCompletableFuture!=null){
            lubboResponseCompletableFuture.complete(response);
            cacheRequest.remove(requestId);
        }
        else  throw new LubboException();
    }


    private   NettyClientHandler clientHandler = null;
    public  void  startConnection(String address) throws InterruptedException {
        clientHandler = new NettyClientHandler();
            Bootstrap b = new Bootstrap();
            EventLoopGroup group = new NioEventLoopGroup();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast("decoder", new ObjectDecoder(ClassResolvers
                                    .weakCachingConcurrentResolver(this.getClass()
                                            .getClassLoader())));
                            pipeline.addLast("encoder", new ObjectEncoder());
                            pipeline.addLast("handler", clientHandler);
                        }
                    });

                b.connect(address, 9000).sync();


    }

    /**
     * 先根据配置，去查找相应的服务地址
     * 创建连接，复用连接
     *
     *
     * 思路好像是这样的，Completablefuture是的类型是Response的类型，
     */
    public void RpcCall(LubboRequest lubboRequest) throws Exception {
        CuratorFramework connection = ZookeeperClient.getConnection();
        String address = ZookeeperUtils.getNodes(connection, lubboRequest.getClassName());
    //    这里拿到的 应该是服务的地址
        clientHandler = null;
        if (cacheConnections.containsKey(address)){
            clientHandler = cacheConnections.get(address);
        }
        else startConnection(address);
    //    现在就可以发送数据了
    //    如果想要异步的发送数据

        CompletableFuture<LubboResponse> completableFuture =  new CompletableFuture<>();
        cacheRequest.put(lubboRequest.getRequestId(),completableFuture);




    }

    public   void Send() throws Exception {
        clientHandler.call();
    }

    public   void Send(String info) throws Exception {
        clientHandler.call(info);
    }
}
