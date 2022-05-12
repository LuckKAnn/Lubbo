package com.luckk.lizzie.remoting.netty;

import com.luckk.lizzie.registry.zk.ZookeeperClient;
import com.luckk.lizzie.registry.zk.ZookeeperUtils;
import com.luckk.lizzie.rpc.exception.LubboException;
import com.luckk.lizzie.rpc.tansport.LubboRequest;
import com.luckk.lizzie.rpc.tansport.LubboResponse;
import com.luckk.lizzie.serialize.LubboMessageDecoder;
import com.luckk.lizzie.serialize.LubboMessageEncoder;
import com.luckk.lizzie.serialize.protostuff.ProtoStuffUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
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


    private static ConcurrentHashMap<String,Channel> cacheConnections = new ConcurrentHashMap<>();


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
    public  Channel  startConnection(String address) throws InterruptedException {
        clientHandler = new NettyClientHandler();
        log.info("start netty client");
            Bootstrap b = new Bootstrap();
            EventLoopGroup group = new NioEventLoopGroup();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            //pipeline.addLast("decoder", new ObjectDecoder(ClassResolvers
                            //        .weakCachingConcurrentResolver(this.getClass()
                            //                .getClassLoader())));
                            //pipeline.addLast("encoder", new ObjectEncoder());
                            pipeline.addLast("decoder",new LubboMessageDecoder(LubboResponse.class));
                            pipeline.addLast("encoder",new LubboMessageEncoder());
                            pipeline.addLast("handler", clientHandler);
                        }
                    });

        ChannelFuture channelFuture = b.connect(address, 9000).sync();
        //TODO 如果调用这个的话，不就是同步阻塞了吗，所以是不是需要加监听器呢
       // channelFuture.channel().closeFuture().sync();
        Channel channel = channelFuture.channel();
        log.info("start netty client end");

        return channel;

    }

    /**
     * 先根据配置，去查找相应的服务地址
     * 创建连接，复用连接
     *
     *
     * 思路好像是这样的，Completablefuture是的类型是Response的类型，
     */
    public void RpcCall(LubboRequest lubboRequest) throws Exception {

        log.info("start rpc call");
        CuratorFramework connection = ZookeeperClient.getConnection();
        String address = ZookeeperUtils.getNodes(connection, lubboRequest.getClassName());
        log.info("获取到的服务器地址是:{}",address);
    //    这里拿到的 应该是服务的地址
        Channel channel = null;
        if (cacheConnections.containsKey(address)){
            channel = cacheConnections.get(address);
        }
        else {
            channel = startConnection(address);
            cacheConnections.put(address,channel);
        }
    //    现在就可以发送数据了
    //    如果想要异步的发送数据

        log.info("发送RPC请求，请求的ID是:[{}]",lubboRequest.getRequestId());
        // 这里为什么不能直接发送？这就是编解码器的问题了
        //channel.writeAndFlush(lubboRequest);
        //byte[] requestSerializ = ProtoStuffUtils.serializer(lubboRequest);
        channel.writeAndFlush(lubboRequest);
        //channel.writeAndFlush("msg from client");


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
