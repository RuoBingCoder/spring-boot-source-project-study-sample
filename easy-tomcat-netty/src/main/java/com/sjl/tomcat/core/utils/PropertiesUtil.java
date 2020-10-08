package com.sjl.tomcat.core.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * @author: JianLei
 * @date: 2020/10/7 10:11 下午
 * @description: Properties
 */
public class PropertiesUtil {

  public static final String SERVER_PORT = "server.port";
  private static final String PROPERTIES_NAME = "web.properties";
  private static final Properties properties = new Properties();

  public static Integer getPort() throws IOException {
    properties.load(PropertiesUtil.class.getClassLoader().getResourceAsStream(PROPERTIES_NAME));
    String port = (String) properties.get(SERVER_PORT);
    if (port != null && !"".equals(port)) {
      return Integer.valueOf(port.trim());
    }
    return null;
  }
}
