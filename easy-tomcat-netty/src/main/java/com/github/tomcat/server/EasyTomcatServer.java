package com.github.tomcat.server;

import cn.hutool.core.lang.Assert;
import com.github.tomcat.core.handle.EasyServerHandler;
import com.github.tomcat.core.utils.PropertiesUtil;
import io.netty.bootstrap.AbstractBootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: JianLei
 * @date: 2020/10/1 6:58 下午
 * @description: EasyTomcat
 * @see DelimiterBasedFrameDecoder 解决拆包粘包 即编码器
 *
 */
@Slf4j
public class EasyTomcatServer {


/*
  private void init() {

    try {
      String webInfo = this.getClass().getResource("/").getPath();
      System.out.println("webInfo path is:" + webInfo);
      FileInputStream ins = new FileInputStream(webInfo + "web.properties");
      webXmlProperties.load(ins);

      for (Object key : webXmlProperties.keySet()) {
        String s = key.toString();
        if (s.endsWith(".url")) {
          String servletName = s.replaceAll("\\.url$", "");
          String url = webXmlProperties.getProperty(s);
          System.out.println("=====>>url is:"+url);
          String className = webXmlProperties.getProperty(servletName + ".className");
          System.out.println("=====>>className is:"+className);
          AbsEasyServlet servlet = (AbsEasyServlet) Class.forName(className).newInstance();
          servletMappings.put(url, servlet);
        }
      }

    } catch (Exception e) {
      log.error("EasyTomcat 出现异常 :", e);
    }
  }
*/

  public void start() {
    EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    /**
     * <code>
     *       static {
     *         DEFAULT_EVENT_LOOP_THREADS = Math.max(1, SystemPropertyUtil.getInt(
     *                 "io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2));
     *
     *         if (logger.isDebugEnabled()) {
     *             logger.debug("-Dio.netty.eventLoopThreads: {}", DEFAULT_EVENT_LOOP_THREADS);
     *         }
     *     }
     * </code>
     * @see register
     */
    EventLoopGroup workGroup = new NioEventLoopGroup(); // 默认cpu核心数的2倍

    try {
      /**
       * @see AbstractBootstrap#initAndRegister()
       * @see io.netty.channel.embedded.EmbeddedEventLoop#register(Channel)
       * @see io.netty.channel.AbstractChannel#register0
       * 双端链表
       * <code>
       *     final ChannelFuture initAndRegister() {
       *        //...
       *         ChannelFuture regFuture = config().group().register(channel); 注册#initChannel()方法
       *         if (regFuture.cause() != null) {
       *             if (channel.isRegistered()) {
       *                 channel.close();
       *             } else {
       *                 channel.unsafe().closeForcibly();
       *             }
       *         }
       * </code>
       */
      ServerBootstrap serverBootstrap = new ServerBootstrap();
      serverBootstrap
          .group(bossGroup, workGroup) // 设置两个线程组
          .channel(NioServerSocketChannel.class) //实力话的是DefaultChannelPipeline
          .option(ChannelOption.SO_BACKLOG, 128) // 设置队列连接数.
          .childOption(ChannelOption.SO_KEEPALIVE, true) // 设置保持连接活动状态
          .childHandler(
              new ChannelInitializer<SocketChannel>() {
                // 给pipeline 设置处理器

                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                  socketChannel
                      .pipeline()
                      // Http 编码器
                      .addLast(new HttpResponseEncoder())
                      .addLast(new HttpRequestDecoder())
                      // http 解码器
                      // 处理RPC请求
                      .addLast(new EasyServerHandler());
                }
              });

      Integer port = PropertiesUtil.getPort();
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
    EasyTomcatServer tomcat = new EasyTomcatServer();
    tomcat.start();
  }
}
