package com.sjl.boot.autowired.inject.sample.annotation;

import com.sjl.boot.autowired.inject.sample.bean.CronScheduled;
import com.sjl.boot.autowired.inject.sample.utils.ThreadPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: JianLei
 * @date: 2020/9/23 7:59 下午
 * @description:
 */
@Component
@Slf4j
public class MyScheduledAnnotationPostProcessor implements BeanPostProcessor {
  @Override
  public Object postProcessBeforeInitialization(Object bean, String beanName)
      throws BeansException {
    if (log.isDebugEnabled()) {
      log.debug("->定时任务后置处理器执行<-");
    }
    Map<Method, Set<MyScheduled>> methodSetMap = mergeMethodsScheduled(bean);
    if (!methodSetMap.isEmpty()) {
      // 执行定时任务
      for (Map.Entry<Method, Set<MyScheduled>> methodSetEntry : methodSetMap.entrySet()) {
        String cron = getCron(methodSetEntry.getValue());
        CronScheduled cronScheduled = createScheduled(cron, methodSetEntry.getKey(), bean);
        ThreadPoolUtil.scheduledTask(cronScheduled);
      }
    }
    return bean;
  }

  private CronScheduled createScheduled(String cron, Method key, Object bean) {
    return new CronScheduled(key, cron, bean);
  }

  private String getCron(Set<MyScheduled> value) {
    if (value.size() == 1) {
      for (MyScheduled myScheduled : value) {
        return myScheduled.cron();
      }
    }
    return "";
  }

  private Map<Method, Set<MyScheduled>> mergeMethodsScheduled(Object bean) {
    Map<Method, Set<MyScheduled>> methodSetMap = new ConcurrentHashMap<>();
    for (Method method : bean.getClass().getMethods()) {
      if (method.isAnnotationPresent(MyScheduled.class)) {
        MyScheduled annotation = method.getAnnotation(MyScheduled.class);
        LinkedHashSet<MyScheduled> hashSet = new LinkedHashSet<>();
        hashSet.add(annotation);
        methodSetMap.put(method, hashSet);
      }
    }
    return methodSetMap;
  }
}
