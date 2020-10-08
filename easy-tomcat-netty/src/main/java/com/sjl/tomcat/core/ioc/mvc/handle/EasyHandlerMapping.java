package com.sjl.tomcat.core.ioc.mvc.handle;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @author: JianLei
 * @date: 2020/10/7 9:58 下午
 * @description: EasyHandlerMapping
 */

public class EasyHandlerMapping {
    private Object controller;	//保存方法对应的实例
    private Method method;		//保存映射的方法
    private Pattern pattern;    //URL的正则匹配

    public EasyHandlerMapping(Pattern pattern,Object controller, Method method) {
        this.controller = controller;
        this.method = method;
        this.pattern = pattern;
    }

    public EasyHandlerMapping() {
    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EasyHandlerMapping that = (EasyHandlerMapping) o;
        return Objects.equals(controller, that.controller) &&
                Objects.equals(method, that.method) &&
                Objects.equals(pattern, that.pattern);
    }

    @Override
    public int hashCode() {
        return Objects.hash(controller, method, pattern);
    }
}
