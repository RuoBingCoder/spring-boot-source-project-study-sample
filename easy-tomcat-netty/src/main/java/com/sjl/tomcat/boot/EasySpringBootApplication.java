package com.sjl.tomcat.boot;

import cn.hutool.core.lang.Assert;
import com.sjl.tomcat.core.annotation.EasyTomcatScan;
import com.sjl.tomcat.core.ioc.context.EasyApplicationContext;
import com.sjl.tomcat.server.EasyTomcatServer;

import java.lang.annotation.Annotation;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author: JianLei
 * @date: 2020/10/8 8:19 下午
 * @description: EasySpringBootApplication
 */
public class EasySpringBootApplication {

  private static final AtomicReference<EasyApplicationContext> easyApplicationContext =
      new AtomicReference<>();

  public static void run(Class<?> clazz) throws Exception {
    Assert.notNull(clazz, "start class not null!");
    initApplicationContext(clazz);
    EasyTomcatServer tomcat = new EasyTomcatServer();
    tomcat.start();
  }

  public static void initApplicationContext(Class<?> clazz) throws Exception {
    for (; ; ) {
      EasyApplicationContext eac = EasySpringBootApplication.easyApplicationContext.get();
      if (eac != null) {
        return;
      }
      eac = new EasyApplicationContext(parseBasePackageName(clazz));
      if (easyApplicationContext.compareAndSet(null, eac)) {
        easyApplicationContext.set(eac);
        return;
      }
    }
  }

  private static String parseBasePackageName(Class<?> clazz) {
    Annotation[] annotations = clazz.getDeclaredAnnotations();
    for (Annotation annotation : annotations) {
      if (annotation.annotationType() == EasyTomcatScan.class) {
        EasyTomcatScan scan = (EasyTomcatScan) annotation;
        return scan.basePackageName();
      }
    }
    return null;
  }

  public static EasyApplicationContext getApplicationContext(){
    Assert.notNull(easyApplicationContext.get(),"applicationContext is null!");
    return easyApplicationContext.get();
  }
}
