package com.github.netty.handler.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author: JianLei
 * @date: 2020/10/25 7:02 下午
 * @description: MessageChannelHandler
 */

@SuppressWarnings("JavaDoc")
public class ClientMessageChannelHandler extends ChannelInboundHandlerAdapter {
    /**
     * @return
     * @Author jianlei.shi
     * @Description 当客户端链接的时候像服务器发送消息¬
     * @Date 7:06 下午 2020/10/25
     * @Param
     **/
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("ClientMessageChannelHandler channelActive ");
        String message="hello server i am is zhangsan";
        ByteBuf buffer = ctx.alloc().buffer(4 * message.length());
        buffer.writeBytes(message.getBytes());
        ctx.write(buffer);
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("ClientMessageChannelHandler.read");
        ByteBuf result = (ByteBuf) msg;
        byte[] bytes = new byte[result.readableBytes()];
        result.readBytes(bytes);
        result.release();
        ctx.close();
        System.out.println("Server say :" + new String(bytes));
    }
}
