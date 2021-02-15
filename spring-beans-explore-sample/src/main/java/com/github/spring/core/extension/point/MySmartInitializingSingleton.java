package com.github.spring.core.extension.point;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author jianlei.shi
 * @date 2021/1/27 7:59 下午
 * @description Smart
 * @see org.springframework.beans.factory.config.ConfigurableListableBeanFactory#preInstantiateSingletons()
 */
@Component
@Slf4j
public class MySmartInitializingSingleton implements SmartInitializingSingleton, ApplicationContextAware {
    private ApplicationContext applicationContext;
    @Override
    public void afterSingletonsInstantiated() {
        final String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();

        System.out.println("  ████████                                ██       ██████             ██  ██ ██                        ██    \n" +
                " ██░░░░░░                                ░██      ██░░░░██           ░██ ░██░██                       ░██    \n" +
                "░██        ██████████   ██████   ██████ ██████   ██    ░░   ██████   ░██ ░██░██       ██████    █████ ░██  ██\n" +
                "░█████████░░██░░██░░██ ░░░░░░██ ░░██░░█░░░██░   ░██        ░░░░░░██  ░██ ░██░██████  ░░░░░░██  ██░░░██░██ ██ \n" +
                "░░░░░░░░██ ░██ ░██ ░██  ███████  ░██ ░   ░██    ░██         ███████  ░██ ░██░██░░░██  ███████ ░██  ░░ ░████  \n" +
                "       ░██ ░██ ░██ ░██ ██░░░░██  ░██     ░██    ░░██    ██ ██░░░░██  ░██ ░██░██  ░██ ██░░░░██ ░██   ██░██░██ \n" +
                " ████████  ███ ░██ ░██░░████████░███     ░░██    ░░██████ ░░████████ ███ ███░██████ ░░████████░░█████ ░██░░██\n" +
                "░░░░░░░░  ░░░  ░░  ░░  ░░░░░░░░ ░░░       ░░      ░░░░░░   ░░░░░░░░ ░░░ ░░░ ░░░░░    ░░░░░░░░  ░░░░░  ░░  ░░ \n");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.applicationContext=applicationContext;
    }
}
