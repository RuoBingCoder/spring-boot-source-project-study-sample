package com.github.netty.handler.server;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * @author: JianLei
 * @date: 2020/10/25 6:48 下午
 * @description: OutBoundChannelHandlerC
 */

public class OutBoundChannelHandlerB extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {

        System.out.println("OutBoundChannelHandlerB.write");
        //执行下一个OutBoundChannelHandler
        ctx.write(msg, promise);
    }

    /**
     * 和write 关联
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
//触发OutBoundChannelHandler 操作 AbstractChannel
        ctx.executor().schedule(() -> {
            ctx.channel().write(Unpooled.copiedBuffer( "########服务器说 hello client ########", CharsetUtil.UTF_8)).addListeners(ChannelFutureListener.CLOSE_ON_FAILURE);
        }, 0, TimeUnit.SECONDS);
        super.handlerAdded(ctx);
    }
}
