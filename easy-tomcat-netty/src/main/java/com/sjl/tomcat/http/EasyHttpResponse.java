package com.sjl.tomcat.http;

/**
 * @author: JianLei
 * @date: 2020/10/8 12:39 下午
 * @description: EasyHttpResponse
 */
public class EasyHttpResponse<T> {
  private Integer code;
  private T data;

  public EasyHttpResponse(Integer code, T data) {
    this.code = code;
    this.data = data;
  }

  public EasyHttpResponse(T data) {
    this.data = data;
  }

  public EasyHttpResponse() {}

  public static <T> EasyHttpResponse success(T data) {
    return new EasyHttpResponse(200, data);
  }

  @Override
  public String toString() {
    return "EasyHttpResponse{" +
            "code=" + code +
            ", data=" + data +
            '}';
  }
}
