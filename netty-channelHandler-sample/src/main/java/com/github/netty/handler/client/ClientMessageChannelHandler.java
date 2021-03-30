package com.github.netty.handler.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: JianLei
 * @date: 2020/10/25 7:02 下午
 * @description: MessageChannelHandler
 */

@SuppressWarnings("JavaDoc")
@Slf4j
public class ClientMessageChannelHandler extends SimpleChannelInboundHandler<ByteBuf> {
    /**
     * @return
     * @Author jianlei.shi
     * @Description 当客户端链接的时候像服务器发送消息¬
     * @Date 7:06 下午 2020/10/25
     * @Param
     **/
   /* @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("ClientMessageChannelHandler channelActive ");
        String message="hello server i am is zhangsan";
        ByteBuf buffer = ctx.alloc().buffer(4 * message.length());
        buffer.writeBytes(message.getBytes());
//        ctx.writeAndFlush(buffer)
        ctx.write(buffer);
        ctx.flush();
    }
*/
  /*  @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("ClientMessageChannelHandler.read");
        ByteBuf result = (ByteBuf) msg;
        byte[] bytes = new byte[result.readableBytes()];
        result.readBytes(bytes);
        result.release();
        ctx.close();
        System.out.println("Server say :" + new String(bytes));
    }*/

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println("ClientMessageChannelHandler.read");
        ByteBuf result = msg;
        byte[] bytes = new byte[result.readableBytes()];
        result.readBytes(bytes);
        System.out.println("Server say :" + new String(bytes));
//        result.release();
//        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("ClientMessageChannelHandler read error",cause);
        super.exceptionCaught(ctx, cause);
    }
}
