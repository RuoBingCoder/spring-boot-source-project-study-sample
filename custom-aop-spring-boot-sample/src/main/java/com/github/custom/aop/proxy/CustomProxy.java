package com.github.custom.aop.proxy;

public interface CustomProxy {


  default <T> T createJDKProxy(Class<?> clazz) {
    return null;
  }
  default <T> T createCGLIBProxy(Class<?> clazz) {
    return null;
  }
}
