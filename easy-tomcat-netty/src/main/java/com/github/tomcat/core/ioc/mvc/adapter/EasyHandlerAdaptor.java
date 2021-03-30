package com.github.tomcat.core.ioc.mvc.adapter;

import com.github.tomcat.core.annotation.EasyRequestParams;
import com.github.tomcat.http.EasyHttpResponse;
import com.github.tomcat.http.EasyRequest;
import com.github.tomcat.http.EasyResponse;
import com.github.tomcat.core.ioc.mvc.handle.EasyHandlerMapping;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: JianLei
 * @date: 2020/10/7 9:59 下午
 * @description: EasyAdaptor
 */
@Slf4j
public class EasyHandlerAdaptor {

    public EasyHttpResponse handle(
            EasyRequest request, EasyResponse response, EasyHandlerMapping handler)
            throws InvocationTargetException, IllegalAccessException {
        Map<String, Integer> paramsIndexMapping = new HashMap<>();
        Annotation[][] parameterAnnotations = handler.getMethod().getParameterAnnotations();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            for (Annotation annotation : parameterAnnotations[i]) {
                if (annotation instanceof EasyRequestParams) {
                    EasyRequestParams easyRequestParams = (EasyRequestParams) annotation;
                    String value = easyRequestParams.value();
                    if (!"".equals(value)) {
                        paramsIndexMapping.put(value, i);
                    }
                }
            }
        }
        // 提取方法中参数
        Class<?>[] parameterTypes = handler.getMethod().getParameterTypes();
        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> type = parameterTypes[i];
            if (type == EasyRequest.class
                    || type == EasyResponse.class
                    || type == String.class
                    || type == Integer.class) {
                paramsIndexMapping.put(type.getName(), i);
            }
        }

        Map<String, List<String>> parameters = request.getParameters();
        // 实参列表
        Object[] paramValues = new Object[parameterTypes.length];
        for (Map.Entry<String, List<String>> param : parameters.entrySet()) {
            String value =
                    Arrays.toString(param.getValue().toArray()).replaceAll("\\[", "").replace("]", "");
            log.info("获取参数中value 转换为字符串为:{}", value);
            if (value.contains("/")) {
                continue;
            }
            if (paramsIndexMapping.containsKey(param.getKey())) {
                Integer index = paramsIndexMapping.get(param.getKey());
                paramValues[index] = caseStringValue(value, parameterTypes[index]);
            }
            if (paramsIndexMapping.containsKey(EasyRequest.class.getName())) {
                int reqIndex = paramsIndexMapping.get(EasyRequest.class.getName());
                paramValues[reqIndex] = request;
            }

            if (paramsIndexMapping.containsKey(EasyResponse.class.getName())) {
                int respIndex = paramsIndexMapping.get(EasyResponse.class.getName());
                paramValues[respIndex] = response;
            }
            Object result = handler.getMethod().invoke(handler.getController(), paramValues);
            if (result == null) {
                return null;
            }

            if (handler.getMethod().getReturnType() == EasyHttpResponse.class) {
                return (EasyHttpResponse) result;
            }
        }
        return null;
    }

    private Object caseStringValue(String value, Class<?> paramsType) {
        if (String.class == paramsType) {
            return value;
        }
        // 如果是int
        if (Integer.class == paramsType) {
            return Integer.valueOf(value);
        } else if (Double.class == paramsType) {
            return Double.valueOf(value);
        } else {
            return value;
        }
    }
}
