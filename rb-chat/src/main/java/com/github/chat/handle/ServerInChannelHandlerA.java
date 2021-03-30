package com.github.chat.handle;

import com.github.chat.protocol.CustomProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author jianlei.shi
 * @date 2021/3/14 7:45 下午
 * @description ServerHandler
 */

public class ServerInChannelHandlerA extends SimpleChannelInboundHandler<CustomProtocol> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CustomProtocol msg) throws Exception {
        System.out.println("server receive: " + msg);
        ctx.fireChannelRead(msg);
    }

    /**
     * 用户事件触发
     *
     * @param ctx ctx
     * @param evt evt
     * @return
     * @author jianlei.shi
     * @date 2021-03-14 20:35:37
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        super.userEventTriggered(ctx, evt);
        if (evt instanceof IdleStateEvent) {

            IdleStateEvent event = (IdleStateEvent) evt;

            if (event.state().equals(IdleState.READER_IDLE)) {
                //未进行读操作
                System.out.println("READER_IDLE");
                // 超时关闭channel
                ctx.close();

            } else if (event.state().equals(IdleState.WRITER_IDLE)) {


            } else if (event.state().equals(IdleState.ALL_IDLE)) {
                //未进行读写
                System.out.println("ALL_IDLE");
                // 发送心跳消息
//                MsgHandleService.getInstance().sendMsgUtil.sendHeartMessage(ctx);

            }
        }
    }
}
