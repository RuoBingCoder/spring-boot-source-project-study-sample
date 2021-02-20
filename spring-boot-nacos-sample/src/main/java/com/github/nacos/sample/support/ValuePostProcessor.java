package com.github.nacos.sample.support;

import com.github.nacos.sample.config.SpringValue;
import com.github.nacos.sample.controller.NacosController;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jianlei.shi
 * @date 2021/2/16 7:40 下午
 * @description ValuePostprocessor
 */
@Component
@Slf4j
public class ValuePostProcessor implements BeanPostProcessor,EnvironmentAware {

    private final Map<String, ValueTarget> targetMap = new ConcurrentHashMap<>();

    public static Map<String, SpringValue> valueTargetMap=new ConcurrentHashMap<>();
    @Override

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        doResolveBeanAttr(bean, beanName);
        return null;
    }

    private void doResolveBeanAttr(Object bean, String beanName) {
        if (bean instanceof NacosController) {
//            targetMap.put(beanName, getValueTarget(bean));
            valueTargetMap.put(beanName,getSpringValue(bean,beanName));
        }
    }

    private SpringValue getSpringValue(Object o, String beanName) {
        return new SpringValue(beanName, getFields(o).get(0), new WeakReference<>(o));
    }

    private ValueTarget getValueTarget(Object bean) {
        ValueTarget vt = new ValueTarget();
        vt.setBean(bean);
        vt.setType(bean.getClass());
        List<Field> fieldList = getFields(bean);
        vt.setFields(fieldList);
        return vt;

    }

    private  List<Field> getFields(Object bean){
        List<Field> fieldList = new ArrayList<>();
        final Class<?> beanClass = bean.getClass();
        final Field[] fields = beanClass.getDeclaredFields();
        if (fields.length > 0) {
            for (Field field : fields) {
                if (field.isAnnotationPresent(Value.class)) {
                    fieldList.add(field);
                }
            }
        }
        return fieldList;
    }
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return null;
    }

/*
    @Override
    public void afterPropertiesSet() {
        final ScheduledThreadPoolExecutor executor = (ScheduledThreadPoolExecutor) ThreadPoolUtils.getExecutor(ThreadTypeEnum.SCHEDULED, false);
        executor.scheduleAtFixedRate(this::doGetNewValue, 2, 20, TimeUnit.SECONDS);
    }

    private void doGetNewValue() {
        RequestParam queryParam = new RequestParam();
        queryParam.setHost("127.0.0.1");
        queryParam.setPort(9988);
        queryParam.setParam("param");
        try {
            final String rowResult = HttpUtils.doGet(queryParam);
            JSONObject jsonObject = JSONObject.parseObject(rowResult);
            for (Map.Entry<String, ValueTarget> entry : targetMap.entrySet()) {
                final List<Field> fields = entry.getValue().getFields();
                final Object bean = entry.getValue().getBean();
                for (Field field : fields) {
                    field.setAccessible(true);
                    field.set(bean, jsonObject.get("data"));
                }
            }
        } catch (Exception e) {
            log.error("doGetNewValue error", e);
            throw new RuntimeException("doGetNewValue error");
        }

    }
*/

    @Override
    public void setEnvironment(Environment environment) {
    }

    @Data
    static class ValueTarget {
        private Object bean;
        private Class<?> type;

        private List<Field> fields;
    }


}
