package com.github.chat.server;

import com.github.chat.codec.IMDecoder;
import com.github.chat.handle.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author jianlei.shi
 * @date 2021/3/14 7:44 下午
 * @description ChatServer
 */

public class ChatServer {
    public void start(Integer port){

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        EventLoopGroup childEventLoopGroup = new NioEventLoopGroup();

        try {
            serverBootstrap
                    .group(eventLoopGroup, childEventLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast(new IMDecoder()); // 解码器
                            ch.pipeline().addLast(new ServerInChannelHandlerA()); // 打印数据
                            ch.pipeline().addLast(new ServerInChannelHandlerB()); // 打印数据
                            ch.pipeline().addLast(new ServerInChannelHandlerC()); // 打印数据
                            ch.pipeline().addLast(new OutBoundChannelHandlerA()); // 打印数据
                            ch.pipeline().addLast(new OutBoundChannelHandlerB()); // 打印数据
                            ch.pipeline().addLast(new OutBoundChannelHandlerC()); // 打印数据
                            ch.pipeline().addLast("ping",new IdleStateHandler(25, 15, 10,TimeUnit.SECONDS));
                        }
                    });

            ChannelFuture future = serverBootstrap.bind("localhost", 9999).sync();

            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                eventLoopGroup.shutdownGracefully().sync();
                childEventLoopGroup.shutdownGracefully().sync();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        new ChatServer().start(null);
    }
}
