package com.github.chat.codec;

import com.github.chat.protocol.CustomProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author jianlei.shi
 * @date 2021/3/14 6:17 下午
 * @description IMDecoder
 * 解码
 */

public class IMDecoder extends ByteToMessageDecoder {
    //匹配消息的正则
    private Pattern pattern = Pattern.compile("^\\[(.*)\\](\\s\\-\\s(.*))?");

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        try {
            final long version = in.readLong();

            byte[] headerBytes = new byte[36]; //uuid length
            in.readBytes(headerBytes); // 读取header
            String header = new String(headerBytes);

            final int length = in.readableBytes();
            final byte[] bytes = new byte[length];
            in.readBytes(bytes);
            final String content = new String(bytes);
            out.add(new CustomProtocol(version, header, content));

        } catch (Exception e) {

        }
    }
}
