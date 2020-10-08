package com.sjl.tomcat.http;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * @author: JianLei
 * @date: 2020/10/1 6:50 下午
 * @description: EasyResponse
 */
@Slf4j
public class EasyResponse {
  private ChannelHandlerContext context;
  private HttpRequest request;

  public EasyResponse(ChannelHandlerContext context, HttpRequest request) {
    this.context = context;
    this.request = request;
  }

  public void write(String out) {
    try {
      if (out == null) {
        assert false;
        if (out.length() == 0) {
          return;
        }
      }
      FullHttpResponse response =
          new DefaultFullHttpResponse(
              HttpVersion.HTTP_1_0,
              HttpResponseStatus.OK,
              Unpooled.wrappedBuffer(out.getBytes(StandardCharsets.UTF_8)));
      response.headers().set("Content-Type", "text/json;charset=utf-8");
      context.writeAndFlush(response);

    } catch (Exception e) {
      log.error("response error! :", e);
    }finally{
      context.flush();
      context.close();
    }
  }
}
