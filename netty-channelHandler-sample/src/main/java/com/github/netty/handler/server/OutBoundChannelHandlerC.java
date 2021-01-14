package com.github.netty.handler.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * @author: JianLei
 * @date: 2020/10/25 6:48 下午
 * @description: OutBoundChannelHandlerC
 */

public class OutBoundChannelHandlerC extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println("OutBoundChannelHandlerC.write->>>");
        //执行下一个OutBoundChannelHandler
        ctx.write(msg, promise);
    }
}
