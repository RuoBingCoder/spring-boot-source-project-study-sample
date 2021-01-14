package com.github.demo.start;

import com.github.tomcat.boot.EasySpringBootApplication;
import com.github.tomcat.core.annotation.EasyTomcatScan;

/**
 * @author: JianLei
 * @date: 2020/10/8 8:15 下午
 * @description: EasyTomcatApplication
 */
@EasyTomcatScan(basePackageName = "com.sjl.demo")
public class EasyTomcatApplication {

  public static void main(String[] args) throws Exception {
        EasySpringBootApplication.run(EasyTomcatApplication.class);
  }
}
