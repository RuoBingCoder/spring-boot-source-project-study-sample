package com.github.simple.core.aop;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author: jianlei.shi
 * @date: 2020/12/15 7:36 下午
 * @description: SimpleAdviseSupport
 */

public class SimpleAdviseSupport implements SimpleAdvise{
    private final List<MethodWrapper> methods;


    public SimpleAdviseSupport(List<MethodWrapper> methods) {
        this.methods = methods;
    }


    @Override
    public Method getMethod(String annotation) {
        return null;
    }

    @Override
    public Object getAspectObject() {
        return null;
    }

    @Override
    public String getPointCut() {
        return null;
    }

    @Override
    public List<MethodWrapper> getAllAspectMethods() {
        return methods;
    }


    public static class MethodWrapper{
        private String name;
        private String annotationName;
        private String annotationInfo;
        private Method method;

        public MethodWrapper(String name, String annotationName, String annotationInfo, Method method) {
            this.name = name;
            this.annotationName = annotationName;
            this.annotationInfo = annotationInfo;
            this.method = method;
        }

        public MethodWrapper(String name, String annotationName, String annotationInfo) {
            this.name = name;
            this.annotationName = annotationName;
            this.annotationInfo = annotationInfo;
        }

        public Method getMethod() {
            return method;
        }

        public void setMethod(Method method) {
            this.method = method;
        }

        public MethodWrapper() {
        }

        public String getAnnotationInfo() {
            return annotationInfo;
        }

        public void setAnnotationInfo(String annotationInfo) {
            this.annotationInfo = annotationInfo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAnnotationName() {
            return annotationName;
        }

        public void setAnnotationName(String annotationName) {
            this.annotationName = annotationName;
        }
    }
}
