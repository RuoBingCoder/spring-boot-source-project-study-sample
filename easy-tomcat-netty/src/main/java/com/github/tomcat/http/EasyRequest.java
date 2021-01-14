package com.github.tomcat.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @author: JianLei
 * @date: 2020/10/1 6:49 下午
 * @description: EasyRequest
 */
public class EasyRequest {
  private ChannelHandlerContext ctx;
  private final HttpRequest request;

  public EasyRequest(ChannelHandlerContext ctx, HttpRequest request) {
    this.ctx = ctx;
    this.request = request;
  }

  public String getUrl() {
    return request.uri();
  }

  public String getMethod() {
    return request.method().name();
  }

  public Map<String, List<String>> getParameters() {
    QueryStringDecoder decoder = new QueryStringDecoder(request.getUri());
    return decoder.parameters();
  }

  public String getParameter(String name) {
    List<String> strings = getParameters().get(name);
    if (CollectionUtils.isEmpty(strings)) {
      return null;
    }

    return strings.get(0);
  }
}
