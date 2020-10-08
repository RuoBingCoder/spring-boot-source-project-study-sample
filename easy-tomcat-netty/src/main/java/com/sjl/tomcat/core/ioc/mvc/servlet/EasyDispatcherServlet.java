package com.sjl.tomcat.core.ioc.mvc.servlet;

import com.sjl.tomcat.boot.EasySpringBootApplication;
import com.sjl.tomcat.core.annotation.EasyController;
import com.sjl.tomcat.core.annotation.EasyPostMapping;
import com.sjl.tomcat.core.annotation.EasyRequestMapping;
import com.sjl.tomcat.core.exception.EasyTomcatException;
import com.sjl.tomcat.core.ioc.context.EasyApplicationContext;
import com.sjl.tomcat.core.ioc.mvc.adapter.EasyHandlerAdaptor;
import com.sjl.tomcat.core.ioc.mvc.handle.EasyHandlerMapping;
import com.sjl.tomcat.http.EasyHttpResponse;
import com.sjl.tomcat.http.EasyRequest;
import com.sjl.tomcat.http.EasyResponse;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: JianLei
 * @date: 2020/10/7 9:55 下午
 * @description: EasyDispatcherServlet
 */
@Slf4j
public class EasyDispatcherServlet extends AbsEasyServlet {
  private final List<EasyHandlerMapping> handlerMappings = new ArrayList<>();
  private final Map<EasyHandlerMapping, EasyHandlerAdaptor> handlerAdaptorMap =
      new ConcurrentHashMap<>();

  public EasyDispatcherServlet() {
    init();
  }

  @Override
  protected void doPost(EasyRequest request, EasyResponse response) {
    try {
      doDispatch(request, response);
    } catch (Exception e) {
      log.error("doDispatch方法出现异常!", e);
    }
  }

  private void doDispatch(EasyRequest request, EasyResponse response) {

    try {
      EasyHandlerMapping handler = getHandler(request);
      if (handler==null){
        return;
      }

      EasyHandlerAdaptor easyHandlerAdaptor = handlerAdaptorMap.get(handler);
      if (easyHandlerAdaptor != null) {
        EasyHttpResponse handle = easyHandlerAdaptor.handle(request, response, handler);
        response.write(handle.toString());
      }

    } catch (Exception e) {
      log.error("handle 出现异常", e);
      throw new EasyTomcatException("handle 出现异常");
    }
  }

  @Override
  protected void doGet(EasyRequest request, EasyResponse response) {
    doPost(request, response);
  }

  @Override
  protected void init() {
    getApplicationContext();
  }

  private void getApplicationContext() {
    EasyApplicationContext eac = EasySpringBootApplication.getApplicationContext();
    initHandlerMapping(eac);
    initHandlerAdapters();
  }

  private void initHandlerAdapters() {
    for (EasyHandlerMapping handlerMapping : handlerMappings) {
      handlerAdaptorMap.put(handlerMapping, new EasyHandlerAdaptor());
    }
  }

  private void initHandlerMapping(EasyApplicationContext ctx) {
    for (Map.Entry<String, Object> entry : ctx.getBeanFactory().entrySet()) {
      if (entry.getValue().getClass().isAnnotationPresent(EasyController.class)
          && entry.getValue().getClass().isAnnotationPresent(EasyRequestMapping.class)) {
        String value1 = entry.getValue().getClass().getAnnotation(EasyRequestMapping.class).value();
        for (Method method : entry.getValue().getClass().getDeclaredMethods()) {
          method.setAccessible(true);
          if (method.isAnnotationPresent(EasyPostMapping.class)) {
            EasyPostMapping postMapping = method.getAnnotation(EasyPostMapping.class);
            StringBuilder endValue = new StringBuilder(postMapping.value());
            endValue.insert(0, value1);
            Pattern pattern = Pattern.compile(endValue.toString());
            handlerMappings.add(new EasyHandlerMapping(pattern, entry.getValue(), method));
          }
        }
      }
    }
  }

  private EasyHandlerMapping getHandler(EasyRequest request) {
    String url = request.getUrl();
    log.info("url is:{}", url);
    if (!url.contains("/favicon.ico")) {
      url = url.substring(0, url.indexOf("?"));
      for (EasyHandlerMapping handlerMapping : handlerMappings) {
        Matcher matcher = handlerMapping.getPattern().matcher(url);
        if (!matcher.matches()) {
          continue;
        }
        return handlerMapping;
      }
    }
    return null;
  }

  public static void main(String[] args) {
    String url = "/say/hello?name=zhangsan";
    url = url.substring(0, url.indexOf("?"));
    System.out.println(url);

    Pattern compile = Pattern.compile("/say/hello");
    Matcher matcher = compile.matcher(url);
    System.out.println(matcher.matches());
  }
}
