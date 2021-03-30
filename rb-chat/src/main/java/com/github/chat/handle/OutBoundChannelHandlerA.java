package com.github.chat.handle;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * @author: JianLei
 * @date: 2020/10/25 6:48 下午
 * @description: OutBoundChannelHandlerC
 */

public class OutBoundChannelHandlerA extends ChannelOutboundHandlerAdapter {

    /*@Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("OutBoundChannelHandlerA.read 发来一条消息\r\n");
        super.read(ctx);
    }*/

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println("OutBoundChannelHandlerA.write");
        //执行下一个OutBoundChannelHandler
//        ctx.writeAndFlush(Unpooled.copiedBuffer( "server get", CharsetUtil.UTF_8)).addListeners(ChannelFutureListener.CLOSE);
        ctx.writeAndFlush(msg);
//        super.write(ctx,msg, promise);
    }

   /* *//**
     * 和write 关联
     *
     * @param ctx
     * @throws Exception
     *//*
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
//触发OutBoundChannelHandler 操作 AbstractChannel
        ctx.executor().execute(() -> {
            ctx.channel().write(Unpooled.copiedBuffer( "server get", CharsetUtil.UTF_8)).addListeners(ChannelFutureListener.CLOSE);
        });
        super.handlerAdded(ctx);
    }*/
}
