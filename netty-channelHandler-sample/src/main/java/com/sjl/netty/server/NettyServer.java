package com.sjl.netty.server;

import cn.hutool.core.lang.Assert;
import com.sjl.netty.handler.server.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * @author: JianLei
 * @date: 2020/10/25 6:46 下午
 * @description: NettyServer
 */

public class NettyServer {
    public static final Integer SERVER_PORT=8089;

    public static void init(int port){
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workGroup = new NioEventLoopGroup(); // 默认cpu核心数的2倍

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap
                    .group(bossGroup, workGroup) // 设置两个线程组
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128) // 设置队列连接数
                    .childOption(ChannelOption.SO_KEEPALIVE, true) // 设置保持连接活动状态
                    .childHandler(
                            new ChannelInitializer<SocketChannel>() {
                                // 给pipeline 设置处理器

                                @Override
                                protected void initChannel(SocketChannel socketChannel) throws Exception {
                                    socketChannel
                                            .pipeline()
                                            // Http 编码器
                                            // http 解码器
                                            // 处理InBound请求
                                            .addLast(new InBoundChannelHandlerA())
                                            .addLast(new InBoundChannelHandlerB())
                                            .addLast(new OutBoundChannelHandlerA())
                                            .addLast(new InBoundChannelHandlerC())
                                            //Out
                                            .addLast(new OutBoundChannelHandlerB())
                                            .addLast(new OutBoundChannelHandlerC());
                                }
                            });
            Assert.notNull(port,"server port is null");
            ChannelFuture future = serverBootstrap.bind(port).sync();
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {

            bossGroup.shutdownGracefully(); // 优雅的关闭
        }


    }

    public static void main(String[] args) {
        init(8089);
    }

}
