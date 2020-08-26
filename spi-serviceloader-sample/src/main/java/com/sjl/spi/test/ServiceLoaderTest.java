package com.sjl.spi.test;

import com.sjl.spi.annotation.ServiceName;
import com.sjl.spi.service.Invoke;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author: JianLei
 * @date: 2020/8/26 7:55 下午
 * @description:
 */
public class ServiceLoaderTest {
    public static void main(String[] args) {
        System.out.println("======================开始====================");
        ServiceLoader<Invoke> serviceLoader = ServiceLoader.load(Invoke.class, Thread.currentThread().getContextClassLoader());
        final Iterator<Invoke> iterator = serviceLoader.iterator();
        while (iterator.hasNext()) {
            final boolean annotationPresent = iterator.next().getClass()
                    .isAnnotationPresent(ServiceName.class);
            if (annotationPresent) {
                System.out.println("=====>>" + iterator.next().invoke("664139849448"));
            }
        }
    }
}
