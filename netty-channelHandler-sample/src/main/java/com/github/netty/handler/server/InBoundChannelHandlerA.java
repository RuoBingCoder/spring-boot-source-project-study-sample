package com.github.netty.handler.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author: JianLei
 * @date: 2020/10/25 6:48 下午
 * @description: InBoundChannelHandlerA
 */

public class InBoundChannelHandlerA extends SimpleChannelInboundHandler<ByteBuf> {

/*
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf result = (ByteBuf) msg;
        byte[] bytes = new byte[result.readableBytes()];
        result.readBytes(bytes);
//        result.release();
        System.out.println("InBoundChannelHandlerA->"+new String(bytes));
        ctx.writeAndFlush("你好客户端").addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
//        ctx.fireChannelRead(msg);
    }*/

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        ByteBuf result = (ByteBuf) msg;
        byte[] bytes = new byte[result.readableBytes()];
        result.readBytes(bytes);
//        result.release();
        System.out.println("InBoundChannelHandlerA->"+new String(bytes));
        ctx.writeAndFlush("你好客户端".getBytes()).addListener(ChannelFutureListener.CLOSE);
    }


}
