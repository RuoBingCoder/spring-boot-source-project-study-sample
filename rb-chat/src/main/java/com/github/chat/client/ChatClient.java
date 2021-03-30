package com.github.chat.client;

import com.github.chat.codec.IMEncoder;
import com.github.chat.handle.ClientHandler;
import com.github.chat.protocol.CustomProtocol;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.UUID;

/**
 * @author jianlei.shi
 * @date 2021/3/14 7:48 下午
 * @description ChatClient
 */

public class ChatClient {

    public void start(){
        Bootstrap bootstrap = new Bootstrap();

        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try {
            bootstrap
                    .group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast(new IMEncoder()); // 编码器
                            ch.pipeline().addLast(new ClientHandler()); // 接收服务端数据
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect("localhost", 9999).sync();

            channelFuture.channel().writeAndFlush(new CustomProtocol(1024l, UUID.randomUUID().toString(), "content detail"));

            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           /* try {
                eventLoopGroup.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
    }

    public static void main(String[] args) {
        new ChatClient().start();
     /*   final String s = UUID.randomUUID().toString();
        System.out.println(s.getBytes().length);
        final CustomProtocol customProtocol = new CustomProtocol(1024l, UUID.randomUUID().toString(), "content detail");
        System.out.println(customProtocol.toString().getBytes().length);
//        ByteBuf buf=new B
        final byte[] bytes = customProtocol.toString().getBytes();
        byte[] versions=new byte[8];
        for (int i = 0; i < versions.length; i++) {
            versions[i]=bytes[i];
        }
        System.out.println("versions :" +new String(versions));*/

      }
}
