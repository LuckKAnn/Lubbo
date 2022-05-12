package com.luckk.lizzie.serialize;

import com.luckk.lizzie.rpc.tansport.LubboRequest;
import com.luckk.lizzie.rpc.tansport.LubboResponse;
import com.luckk.lizzie.serialize.protostuff.ProtoStuffUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @FileName: LubboMessageEncoder
 * @Author: LuckKun
 * @Email: 1546165200@qq.com
 * @Date: 2022/5/12 17:35
 */
@Slf4j
//这里直接以LubboRequest作为泛型不好，能不能就当作泛型？
public class LubboMessageEncoder extends MessageToByteEncoder{

    protected void encode(ChannelHandlerContext channelHandlerContext, LubboRequest lubboRequest, ByteBuf byteBuf) throws Exception {
        log.info("processing encoding ");
        //这里都是byteBuf

        //    encode要做什么？序列化，然后把数据发送出去

        byte[] serializer = ProtoStuffUtils.serializer(lubboRequest);
        byteBuf.writeInt(serializer.length);
        byteBuf.writeBytes(serializer);

    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        log.info("processing encoding ");
        //这里都是byteBuf

        //    encode要做什么？序列化，然后把数据发送出去
        if(o instanceof LubboResponse){
            LubboResponse response = (LubboResponse) o;
            byte[] serializer = ProtoStuffUtils.serializer(response);
            byteBuf.writeInt(serializer.length);
            byteBuf.writeBytes(serializer);
        }
        else if(o instanceof LubboRequest){
            LubboRequest request = (LubboRequest) o;
            byte[] serializer = ProtoStuffUtils.serializer(request);
            byteBuf.writeInt(serializer.length);
            byteBuf.writeBytes(serializer);
        }

    }
}
