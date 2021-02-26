package com.github.spring.components.learning.lighthttp.handler;

import com.alibaba.fastjson.JSONObject;
import com.github.spring.components.learning.exception.LightHttpException;
import com.github.spring.components.learning.exception.LightHttpTypeConvertException;
import common.constants.Constants;
import http.RequestParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import utils.ClassUtils;
import utils.HttpUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author jianlei.shi
 * @date 2021/2/25 11:16 上午
 * @description Rpc 处理
 */
@Slf4j
public class LightHttpInvocationHandler extends AbstractLightHttpInvocation {

    public LightHttpInvocationHandler(Class<?> interfaces, ConfigurableBeanFactory beanFactory) {
        super(beanFactory, interfaces);

    }

    /**
     * 异步调用程序
     *
     * @param urlWrapper url包装
     * @param args       参数
     * @param executor   执行器
     * @return {@link Object }
     * @author jianlei.shi
     * @date 2021-02-26 11:53:43
     */
    @Override
    public Object asyncInvoker(LightHttpHolder lightHttpHolder) {
        ThreadPoolExecutor executor = null;
        try {
            if (lightHttpHolder.getT() instanceof UrlWrapper) {
                executor = lightHttpHolder.getExecutor();
                final Object[] args = lightHttpHolder.getArgs();
                final UrlWrapper urlWrapper = (UrlWrapper) lightHttpHolder.getT();

                if (Constants.POST.equals(urlWrapper.getRqMethod())) {
                    final Future<String> future = executor.submit(() -> HttpUtils.doPost(buildRq(urlWrapper, args, true)));
                    final Class<?> returnType = lightHttpHolder.getReturnType();
                    final String rowRes = future.get();
                    return strToReturnType(rowRes, returnType);
                } else if (Constants.GET.equals(urlWrapper.getRqMethod())) {
                    final Future<String> future = executor.submit(() -> HttpUtils.doPost(buildRq(urlWrapper, args, true)));
                    final Class<?> returnType = lightHttpHolder.getReturnType();
                    final String rowRes = future.get();
                    return strToReturnType(rowRes, returnType);
                } else {
                    throw new LightHttpException("方法中没有找到请求注解,请添加注解: @Post 或者 @Get!");
                }
            }
        } catch (Exception e) {
            log.error("Rpc invoke error", e);
            assert executor != null;
            executor.shutdown();
            throw new LightHttpException("Rpc invoke error!");
        }
       return null;
    }

    private Object strToReturnType(String rowRes, Class<?> returnType) {
        try {
            log.info("开始将返回结果转换为return结果类型 row :{}", rowRes);
            final Object result = JSONObject.parseObject(rowRes, returnType);
            log.info("转化为return type success");
            return result;
        } catch (Exception e) {
            log.error("String转化为return type exception", e);
            throw new LightHttpTypeConvertException("String转化为return type exception");
        }

    }

    private RequestParam buildRq(AbstractLightHttpInvocation.UrlWrapper urlWrapper, Object[] args, boolean isPost) {
        RequestParam requestParam = new RequestParam();
        Map<String, Object> body = new HashMap<>();
        try {
            if (args.length >= 1) {
                for (Object arg : args) {
                    final Field[] fields = ClassUtils.getFields(arg.getClass());
                    for (Field field : fields) {
                        field.setAccessible(true);
                        body.put(field.getName(), field.get(arg));
                    }
                }
                requestParam.setBody(body);
                requestParam.setIsPost(isPost);
                requestParam.setSource(urlWrapper.getResource());
                requestParam.setBaseUrl(urlWrapper.getBaseUrl());

            }
        } catch (Exception e) {
            log.error("buildRq exception", e);
            throw new RuntimeException("buildRq exception : 【" + e.getMessage() + "】");
        }
        return requestParam;
    }


}
