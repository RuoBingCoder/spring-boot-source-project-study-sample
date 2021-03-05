package com.github.spring.components.learning.lighthttp.handler;

import com.github.spring.components.learning.exception.LightHttpException;
import com.github.spring.components.learning.lighthttp.annotation.Get;
import com.github.spring.components.learning.lighthttp.annotation.LightHttpClient;
import com.github.spring.components.learning.lighthttp.annotation.Post;
import com.github.spring.components.learning.lighthttp.store.LightHttpStore;
import common.constants.Constants;
import helper.PlaceholderHelper;
import helper.ThreadPoolHelper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import utils.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author jianlei.shi
 * @date 2021/2/25 4:08 下午
 * @description AbsInvocation
 */
@Slf4j
public abstract class AbstractLightHttpInvocation implements InvocationHandler {

    private final ConfigurableBeanFactory beanFactory;
    private final Map<Method, UrlWrapper> METHOD_CACHE = new ConcurrentHashMap<>();
    private  ThreadPoolExecutor executor;
    private final Lock LOCK = new ReentrantLock();
    private volatile boolean isStop = false;
    protected final Class<?> interfaces;


    protected AbstractLightHttpInvocation(ConfigurableBeanFactory beanFactory, Class<?> interfaces) {
        this.beanFactory = beanFactory;
        this.interfaces = interfaces;
        this.executor = getThreadPool(beanFactory);
        processAddStore(executor, beanFactory);

    }

    private ThreadPoolExecutor getThreadPool(ConfigurableBeanFactory beanFactory) {
        final ThreadPoolHelper poolHelper = beanFactory.getBean(ThreadPoolHelper.class);
        return poolHelper.getDefaultExecutor();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (log.isTraceEnabled()) {
            log.trace("interfaces name:{} method name:{}", interfaces.getName(), method.getName());
        }
        final Annotation metaData = AnnotationUtils.getAnnotationMetaData(interfaces, LightHttpClient.class);
        if (metaData instanceof LightHttpClient) {
            LightHttpClient lightHttpClient = (LightHttpClient) metaData;
            final String baseUrl = lightHttpClient.baseUrl();
            return doInvoke(baseUrl, method, args);
        }
        return new Object();
    }

    private void processAddStore(ThreadPoolExecutor executor, ConfigurableBeanFactory beanFactory) {
        final LightHttpStore httpStore = beanFactory.getBean(LightHttpStore.class);
        httpStore.addThreads(executor);
    }


    protected Object doInvoke(String baseUrl, Method method, Object[] args) {
        String base_url;
        checkUrl(baseUrl);
        // 确保@LightHttpClient(baseUrl = "${sight.base.url}")
        if (baseUrl.startsWith(PlaceholderHelper.getPlaceholderPrefix()) && baseUrl.endsWith(PlaceholderHelper.getPlaceholderSuffix())) {
            final PlaceholderHelper helper = beanFactory.getBean(PlaceholderHelper.class);
            base_url = (String) helper.resolvePropertyValue(beanFactory, baseUrl);
        } else {
            //@LightHttpClient(baseUrl = "http://localhost:8080/test/....")
            base_url = baseUrl;
        }
        //先从缓存中获取
        if (METHOD_CACHE.get(method) != null) {
            final UrlWrapper urlWrapper = METHOD_CACHE.get(method);
            try {
                return asyncInvoker(getHolder(method, urlWrapper, args, executor));
            } catch (Exception e) {
                log.error("doInvoke http error", e);
                throw new LightHttpException("doInvoke http error msg:【 " + e.getMessage() + " 】");
            }

        } else {
            Method[] methods = new Method[]{method};
            final Map annotations = AnnotationUtils.findAnnotationMethod(methods, new Class[]{Get.class, Post.class});
            LOCK.lock();
            try {
                addCache(base_url, annotations);
                final UrlWrapper urlWrapper = METHOD_CACHE.get(method);
                return asyncInvoker(getHolder(method, urlWrapper, args, executor));
            } catch (Exception e) {
                log.error("doInvoke http error", e);
                METHOD_CACHE.clear();
                throw new LightHttpException("doInvoke http error msg:【 " + e.getMessage() + " 】");
            } finally {
                LOCK.unlock();
            }

        }

    }

    private LightHttpHolder<UrlWrapper> getHolder(Method method, UrlWrapper urlWrapper, Object[] args, ThreadPoolExecutor executor) {
        return new LightHttpHolder<>(urlWrapper, method.getName(),args, executor, method.getReturnType());
    }


    protected void checkUrl(String baseUrl) {
        if (StringUtils.isBlank(baseUrl)) {
            throw new LightHttpException("base url 不能为null ");
        }
    }

    protected void addCache(String value, Map<Method, Class<? extends Annotation>> annotations) {
        for (Map.Entry<Method, Class<? extends Annotation>> methodClassEntry : annotations.entrySet()) {
            if (!METHOD_CACHE.containsKey(methodClassEntry.getKey())) {
                METHOD_CACHE.put(methodClassEntry.getKey(), new UrlWrapper(value, getResource(methodClassEntry.getKey(), methodClassEntry.getValue()), (methodClassEntry.getValue() == Post.class ? Constants.POST : Constants.GET)));
            }
        }
    }

    protected String getResource(Method method, Class<? extends Annotation> value) {
        final Annotation annotation = method.getAnnotation(value);
        if (annotation instanceof Post) {
            Post lPost = (Post) annotation;
            return lPost.resource();
        }
        if (annotation instanceof Get) {
            Get lGet = (Get) annotation;
            return lGet.resource();
        }
        return null;
    }

    protected abstract <T> Object asyncInvoker(LightHttpHolder<T> lightHttpHolder);


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class UrlWrapper {
        private String baseUrl;
        private String resource;
        private String RqMethod;

    }
}


