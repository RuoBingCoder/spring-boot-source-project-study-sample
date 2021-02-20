package utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author jianlei.shi
 * @date 2021/2/19 7:21 下午
 * @description SpringUtils
 */
@Slf4j
public class SpringUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtils.applicationContext = applicationContext;
    }

    public static <T> T getBeanByType(Class<T> clazz) {
        try {
            return applicationContext.getBean(clazz);
        } catch (Exception e) {
            log.error("SpringUtils  getBeanByType error!", e);
            throw new RuntimeException("SpringUtils  getBeanByType error");
        }
    }


    public static <T> T getBeanByName(String name) {
        try {
            return (T) applicationContext.getBean(name);
        } catch (Exception e) {
            log.error("SpringUtils  getBeanByName error!", e);
            throw new RuntimeException("SpringUtils  getBeanByName error");
        }
    }
}
