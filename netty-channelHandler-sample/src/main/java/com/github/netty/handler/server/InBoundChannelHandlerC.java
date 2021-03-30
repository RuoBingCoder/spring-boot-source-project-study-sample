package com.github.netty.handler.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author: JianLei
 * @date: 2020/10/25 6:48 下午
 * @description: InBoundChannelHandlerA
 */

public class InBoundChannelHandlerC extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
       /* ByteBuf result = (ByteBuf) msg;
        byte[] bytes = new byte[result.readableBytes()];
        result.readBytes(bytes);*/
//        result.release();
        System.out.println("InBoundChannelHandlerC");
//        ctx.writeAndFlush("你好服务器!").addListeners(ChannelFutureListener.CLOSE_ON_FAILURE);
        ctx.fireChannelRead(msg);
    }
}
