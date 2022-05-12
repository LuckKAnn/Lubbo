import com.luckk.lizzie.remoting.netty.NettyClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @FileName: MyTest
 * @Author: LuckKun
 * @Email: 1546165200@qq.com
 * @Date: 2022/5/12 15:50
 */
@Slf4j
public class MyTest {



    @Test
    public void myTest(){
        System.out.println("test");
        log.info("log.......");
    }


    @Test
    public void connectedNetty(){
        NettyClientHandler clientHandler = new NettyClientHandler();
        log.info("start netty client");
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

        try {
            ChannelFuture sync = b.connect("127.0.0.1", 9000).sync();
            sync.channel().writeAndFlush("msg");
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("start netty client end");
    }


}
