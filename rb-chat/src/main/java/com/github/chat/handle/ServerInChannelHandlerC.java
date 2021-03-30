package com.github.chat.handle;

import com.github.chat.protocol.CustomProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author: JianLei
 * @date: 2020/10/25 6:48 下午
 * @description: InBoundChannelHandlerA
 */

public class ServerInChannelHandlerC extends SimpleChannelInboundHandler<CustomProtocol> {

  /*  @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
       *//* ByteBuf result = (ByteBuf) msg;
        byte[] bytes = new byte[result.readableBytes()];
        result.readBytes(bytes);*//*
//        result.release();
        System.out.println("InBoundChannelHandlerC");
        ctx.writeAndFlush(Unpooled.copiedBuffer( "server get", CharsetUtil.UTF_8)).addListener(ChannelFutureListener.CLOSE);

//        ctx.writeAndFlush("你好服务器!").addListeners(ChannelFutureListener.CLOSE_ON_FAILURE);
//        ctx.fireChannelRead(msg);
    }*/

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CustomProtocol msg) throws Exception {
        System.out.println("InBoundChannelHandlerC");
//        ctx.writeAndFlush(Unpooled.copiedBuffer( "server get", CharsetUtil.UTF_8)).addListener(ChannelFutureListener.CLOSE);
        ctx.fireChannelRead(msg);
    }
}
