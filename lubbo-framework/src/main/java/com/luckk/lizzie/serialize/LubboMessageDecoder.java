package com.luckk.lizzie.serialize;

import com.luckk.lizzie.serialize.protostuff.ProtoStuffUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @FileName: LubboMessageDecoder
 * @Author: LuckKun
 * @Email: 1546165200@qq.com
 * @Date: 2022/5/12 17:37
 */
public class LubboMessageDecoder extends ByteToMessageDecoder {


    private Class<?> targetClass;


    public LubboMessageDecoder(Class<?> targetClass){
        this.targetClass = targetClass;
    }


    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
        //K2 why?
        if (in.readableBytes() < 4) {
            return;
        }
        in.markReaderIndex();
        int dataLength = in.readInt();
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return;
        }
        byte[] data = new byte[dataLength];
        in.readBytes(data);
        out.add(ProtoStuffUtils.deserializer(data, targetClass));

    }
}
