package com.github.spring.components.lightweight.test.sample;

import com.alibaba.fastjson.JSONObject;
import config.ThreadPoolPropertiesConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import utils.AnnotationUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@SpringBootTest
class SpringComponentsLightweightTestSampleApplicationTests {

    private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    final ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    final ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
/*
   @Autowired
    private UseBinder useBinder;*/

   @Autowired
   private ThreadPoolPropertiesConfig propertiesConfig; //验证binder

    void testReaderLock() {
        readLock.lock();
        try {
            int count = 0;
            for (int i = 0; i <1000 ; i++) {
                count+=i;
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           readLock.unlock();
        }
    }

    @Test
    void contextLoads() {
    }
    @Test
    public void binderTest() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//       final Object o = useBinder.v2(null, "simple.thread.pool", Map.class);
//        System.out.println(JSONObject.toJSONString(o));
        System.out.println(JSONObject.toJSONString(propertiesConfig));


    }

    public void nativeTest() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final Class<?> aClass = Class.forName("com.github.spring.components.lightweight.test.sample.bind.UseBinder");
        final Method method = aClass.getDeclaredMethod("test", String.class);
        Object res = method.invoke(null, "hello");
        System.out.println(res);
    }

    public static void main(String[] args) {
        System.out.println(65536 & 65535);
//        System.out.println(SpringComponentsLightweightTestSampleApplication.class.isAnnotationPresent(Import.class));
        final Set<Class<?>> importMetadata = AnnotationUtils.getImportMetadata(SpringComponentsLightweightTestSampleApplication.class);
        for (Class<?> aClass : importMetadata) {
            System.out.println(aClass.getName());
        }

    }

}
