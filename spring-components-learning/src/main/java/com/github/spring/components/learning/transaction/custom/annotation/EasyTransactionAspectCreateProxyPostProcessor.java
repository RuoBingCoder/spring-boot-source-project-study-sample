package com.github.spring.components.learning.transaction.custom.annotation;

import cn.hutool.core.lang.Assert;
import com.github.spring.components.learning.transaction.custom.proxy.ProxyFactory;
import com.github.spring.components.learning.transaction.custom.suuport.TransactionAdviserSupport;
import com.github.spring.components.learning.transaction.service.impl.StateOperateServiceImpl;
import com.github.spring.components.learning.utils.SpringUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.DependsOn;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * @author: JianLei
 * @date: 2020/11/8 12:14 下午
 * @description: 事务切面
 */
@Component
@DependsOn("springUtil")
@Slf4j
public class EasyTransactionAspectCreateProxyPostProcessor implements BeanPostProcessor {


    @Override
    public Object postProcessBeforeInitialization(@NonNull Object bean,@NonNull String beanName) throws BeansException {
        if (bean instanceof StateOperateServiceImpl){
//            log.info("********>> BeforeInitialization bean is:{}",bean.getClass());
        }
        return null;
    }

    @SneakyThrows
    @Override
    public Object postProcessAfterInitialization(@NonNull Object bean, @NonNull String beanName) throws BeansException {
//        log.info("===>开始执行postProcessAfterInitialization 当前bean :{}", bean.getClass().getSimpleName());
        Object service = getProxyService(bean);
        if (service != null) {
            TransactionAdviserSupport support = new TransactionAdviserSupport(service.getClass(), service);
            createProxy(bean, support);
        }
        return null;
    }

    private Object getProxyService(Object bean) {
        Set<Object> servicesBeans = SpringUtil.getServicesBeans();
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(EasyAutowired.class)) {
                for (Object servicesBean : servicesBeans) {
                    if (servicesBean.getClass().getInterfaces()[0].equals(field.getType())) {
                        return servicesBean;
                    }
                }
            }
        }
        return null;
    }

    private void createProxy(Object bean, TransactionAdviserSupport support) throws IllegalAccessException {
        Field[] fields = bean.getClass().getDeclaredFields();
        Field field = getProxyField(fields);
        assert field != null;
        field.setAccessible(true);
        field.set(bean, ProxyFactory.newProxyFactory(support).createProxy(null));
    }

    private Field getProxyField(Field[] fields) {
        Assert.notNull(fields);
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(EasyAutowired.class)) {
                return field;
            }
        }
        return null;
    }

}
