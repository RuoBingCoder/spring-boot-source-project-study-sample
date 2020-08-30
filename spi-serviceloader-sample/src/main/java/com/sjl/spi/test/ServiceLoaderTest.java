package com.sjl.spi.test;

import com.esotericsoftware.reflectasm.MethodAccess;
import com.sjl.spi.annotation.ServiceName;
import com.sjl.spi.service.Invoke;
import com.sjl.spi.service.impl.DubboInvokeImpl;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author: JianLei
 * @date: 2020/8/26 7:55 下午
 * @description:
 */
public class ServiceLoaderTest {
  public static void main(String[] args) throws IllegalAccessException, InstantiationException {
    System.out.println("======================开始====================");
    ServiceLoader<Invoke> serviceLoader =
        ServiceLoader.load(Invoke.class, Thread.currentThread().getContextClassLoader());
    final Iterator<Invoke> iterator = serviceLoader.iterator();
    while (iterator.hasNext()) {
      final boolean annotationPresent =
          iterator.next().getClass().isAnnotationPresent(ServiceName.class);
      if (annotationPresent) {
        System.out.println("=====>>" + iterator.next().invoke("664139849448"));
      }
    }
    String min = "200000.12";
    String max = "1000";
    BigDecimal minMoney = new BigDecimal(min);
    BigDecimal maxMoney = new BigDecimal(max);
    System.out.println(minMoney.compareTo(maxMoney));
    int minValueBits = new BigDecimal("198000000000000000000.2").precision();
    System.out.println(minValueBits);

    MethodAccess methodAccess = MethodAccess.get(DubboInvokeImpl.class);
    Object invoke = methodAccess.invoke(DubboInvokeImpl.class.newInstance(), "invoke", "455666");
    System.out.println("--->>"+invoke.toString());
  }
}
