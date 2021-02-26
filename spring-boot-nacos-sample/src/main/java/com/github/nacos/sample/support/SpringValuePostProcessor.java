package com.github.nacos.sample.support;

import com.github.nacos.sample.config.SpringValue;
import com.github.nacos.sample.config.listener.AutoUpdateConfigChangeListener;
import com.github.nacos.sample.controller.NacosController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author jianlei.shi
 * @date 2021/2/16 7:40 下午
 * @description ValuePostprocessor
 */
@Component
@Slf4j
public class SpringValuePostProcessor extends AutoUpdateConfigChangeListener implements BeanPostProcessor,EnvironmentAware {


    protected SpringValuePostProcessor(ConfigurableEnvironment environment, ConfigurableBeanFactory beanFactory) {
        super(environment, beanFactory);
    }

    public SpringValuePostProcessor() {
    }

    @Override

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        doResolveBeanAttr(bean, beanName);
        return null;
    }

    private void doResolveBeanAttr(Object bean, String beanName) {
        if (bean instanceof NacosController) {
//            targetMap.put(beanName, getValueTarget(bean));
            targetValueMap.put(beanName,getSpringValue(bean,beanName));
        }
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

    @Override
    protected Map<String, SpringValue> getValues() {
        return null;
    }



}
