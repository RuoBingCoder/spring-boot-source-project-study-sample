package com.sjl.boot.autowired.inject.sample.annotation;

import com.sjl.boot.autowired.inject.sample.bean.CronScheduled;
import com.sjl.boot.autowired.inject.sample.utils.ReflectiveUtil;
import com.sjl.boot.autowired.inject.sample.utils.ThreadPoolUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author: JianLei
 * @date: 2020/9/23 7:59 下午
 * @description:
 * @see ApplicationContextAwareProcessor #postProcessBeforeInitialization
 */
@Component
@Slf4j
public class CustomScheduledAnnotationPostProcessor implements BeanPostProcessor, EmbeddedValueResolverAware {

    @SneakyThrows
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        if (log.isDebugEnabled()) {
            log.debug("->定时任务后置处理器执行<-");
        }
        Map<Method, Set<CustomScheduled>> methodSetMap = mergeMethodsScheduled(bean);
        if (!methodSetMap.isEmpty()) {
            // 执行定时任务
            for (Map.Entry<Method, Set<CustomScheduled>> methodSetEntry : methodSetMap.entrySet()) {
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

    private String getCron(Set<CustomScheduled> value) {
        if (value.size() == 1) {
            for (CustomScheduled myScheduled : value) {
                return myScheduled.cron();
            }
        }
        return "";
    }

    private Map<Method, Set<CustomScheduled>> mergeMethodsScheduled(Object bean) throws NoSuchMethodException {
        Map<Method, Set<CustomScheduled>> methodSetMap = new ConcurrentHashMap<>();
        addScheduledMethods(bean, methodSetMap);
        return methodSetMap;
    }

    private void addScheduledMethods(Object bean, Map<Method, Set<CustomScheduled>> methodSetMap) throws NoSuchMethodException {
        for (Method method : ReflectiveUtil.getMethods(bean.getClass(), null)) {
            if (method.isAnnotationPresent(CustomScheduled.class)) {
                CustomScheduled annotation = method.getAnnotation(CustomScheduled.class);
                LinkedHashSet<CustomScheduled> hashSet = new LinkedHashSet<>();
                hashSet.add(annotation);
                methodSetMap.put(method, hashSet);
            }
        }
    }

    /**
     * 解析字符串
     *
     * @param resolver
     */
    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        String result = resolver.resolveStringValue("你好${app.name}, 计算#{3*8}");
        System.out.println("解析的字符串为---" + result);
    }
}
