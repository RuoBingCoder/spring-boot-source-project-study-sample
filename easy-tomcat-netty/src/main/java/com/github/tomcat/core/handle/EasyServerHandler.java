package com.github.tomcat.core.handle;

import com.alibaba.fastjson.JSONObject;
import com.github.tomcat.core.ioc.mvc.servlet.EasyDispatcherServlet;
import com.github.tomcat.http.EasyRequest;
import com.github.tomcat.http.EasyResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: JianLei
 * @date: 2020/10/1 10:12 下午
 * @description: EasyServerHandler
 * @see DefaultChannelPipeline 每个channle对应一个pipline
 */
@Slf4j
public class EasyServerHandler extends ChannelInboundHandlerAdapter {

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    if (msg instanceof HttpRequest) {
      HttpRequest request = (HttpRequest) msg;
      // 装交给request实现
      EasyRequest rq = new EasyRequest(ctx, request);
      log.info("===>params:{}" , JSONObject.toJSONString(rq.getParameters()));
      EasyResponse resp = new EasyResponse(ctx, request);
      EasyDispatcherServlet easyDispatcherServlet = new EasyDispatcherServlet();
      easyDispatcherServlet.service(rq,resp);

    }


  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    log.info("##########server active ......");
    super.channelActive(ctx);
  }
}
