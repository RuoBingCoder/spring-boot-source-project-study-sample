package com.github.chat.handle;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @author jianlei.shi
 * @date 2021/3/14 7:49 下午
 * @description ClientHandler
 */

public class ClientHandler extends SimpleChannelInboundHandler<ByteBuf>{
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println("send success, response is: " + msg.toString(CharsetUtil.UTF_8));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
      /*  SocketChannel channel = (SocketChannel) ctx.channel();
        System.out.println("链接报告开始");
        System.out.println("链接报告信息：有一客户端链接到本服务端");
        System.out.println("链接报告IP:" + channel.getLocalAddress());
        System.out.println("链接报告Port:" + channel.getLocalAddress().toString());
        System.out.println("链接报告完毕");
        //通知客户端链接建立成功
        String str = "通知客户端链接建立成功" + " " + new Date() + " " + channel.getLocalAddress().toString() + "\r\n";
        //ctx.writeAndFlush(str);*/
    }
}
