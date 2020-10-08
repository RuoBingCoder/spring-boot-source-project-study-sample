package com.sjl.tomcat.core.ioc.mvc.servlet;

import com.sjl.tomcat.http.EasyRequest;
import com.sjl.tomcat.http.EasyResponse;

import java.io.IOException;

/**
 * @author: JianLei
 * @date: 2020/10/1 7:00 下午
 * @description: EasyServlet
 */
public abstract class AbsEasyServlet {

  protected static final String DO_GET = "Get";
  protected static final String DO_POST = "Post";

  public void service(EasyRequest request, EasyResponse response) {
    if (DO_GET.equalsIgnoreCase(request.getMethod())) {
      doGet(request, response);
    } else {
      doPost(request, response);
    }
  }

  protected abstract void doPost(EasyRequest request, EasyResponse response);

  protected abstract void doGet(EasyRequest request, EasyResponse response);


  protected abstract void init() throws Exception;
}
