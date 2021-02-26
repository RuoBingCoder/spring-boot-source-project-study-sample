package com.github.spring.components.learning.lighthttp.scanner;

import com.github.spring.components.learning.lighthttp.support.LightHttpFactoryBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

import java.util.Objects;
import java.util.Set;

/**
 * @author jianlei.shi
 * @date 2021/2/25 10:58 上午
 * @description LightHttpClassPathBeanDefinitionScanner
 */
@Slf4j
public class LightHttpClassPathBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {
    public LightHttpClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        super(registry);
    }

    private final LightHttpFactoryBean<Object> factoryBean = new LightHttpFactoryBean<>();

    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages) {
        final Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
        if (beanDefinitionHolders.isEmpty()) {
            log.warn("No LightHttp BeanDefinition!");
        } else {
            processorBeanDefinitions(beanDefinitionHolders);
        }
        return super.doScan(basePackages);
    }

    private void processorBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitionHolders) {
        GenericBeanDefinition beanDefinition;
        for (BeanDefinitionHolder beanDefinitionHolder : beanDefinitionHolders) {
            beanDefinition = (GenericBeanDefinition) beanDefinitionHolder.getBeanDefinition();
            beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(Objects.requireNonNull(beanDefinition.getBeanClassName()));
            beanDefinition.setBeanClass(this.factoryBean.getClass());
            beanDefinition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
        }

    }


    /**
     * 所有接口注册匹配
     *
     * @return
     * @author jianlei.shi
     * @date 2021-02-25 16:23:47
     */
    public void registryAllInterfaces() {
        addIncludeFilter((metadataReader, metadataReaderFactory) -> true);
    }

    /**
     * 所有的class 都被扫描到
     *
     * @param beanDefinition bean定义
     * @return boolean
     * @author jianlei.shi
     * @date 2021-02-25 11:59:35
     */
    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
    }

}
