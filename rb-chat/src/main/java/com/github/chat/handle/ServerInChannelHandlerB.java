package com.github.chat.handle;

import com.github.chat.protocol.CustomProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author: JianLei
 * @date: 2020/10/25 6:48 下午
 * @description: InBoundChannelHandlerA
 */

public class ServerInChannelHandlerB extends SimpleChannelInboundHandler<CustomProtocol> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CustomProtocol msg) throws Exception {
      /*  ByteBuf result = (ByteBuf) msg;
        byte[] bytes = new byte[result.readableBytes()];
        result.readBytes(bytes);*/
//        result.release();
        System.out.println("InBoundChannelHandlerB");
        ctx.fireChannelRead(msg);
    }
}
