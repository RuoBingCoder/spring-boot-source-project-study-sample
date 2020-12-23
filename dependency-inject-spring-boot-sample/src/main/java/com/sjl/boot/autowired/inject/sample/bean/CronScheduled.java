package com.sjl.boot.autowired.inject.sample.bean;

import java.lang.reflect.Method;

/**
 * @author: JianLei
 * @date: 2020/9/24 11:09 上午
 * @description:
 */
public class CronScheduled {
    private Method method;
    private String cron;
    private Object bean;

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public CronScheduled() {
    }

    public CronScheduled(Method method, String cron, Object bean) {
        this.method = method;
        this.cron = cron;
        this.bean = bean;
    }
}
