package com.github.chat.codec;

import com.github.chat.protocol.CustomProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author jianlei.shi
 * @date 2021/3/14 7:40 下午
 * @description IMEncoder
 *编码
 */

public class IMEncoder extends MessageToByteEncoder<CustomProtocol> {


    @Override
    protected void encode(ChannelHandlerContext ctx, CustomProtocol msg, ByteBuf out) throws Exception {
        out.writeLong(msg.getVersion());
        out.writeBytes(msg.getHeader().getBytes());
        out.writeBytes(msg.getContent().getBytes());
    }
}
