package com.github.spring.components.lightweight.test.sample;

import com.github.common.annotation.EnableAutoConfigThreadPool;
import com.github.spring.components.lightweight.test.sample.service.ServiceBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

/**
 *
 * @see org.springframework.core.io.support.SpringFactoriesLoader //@EnableAutoConfiguration META-INF/spring.factories
 * <code>
 *     即获取META-INF/spring.factories 所有class 再次通过processImports() 过滤是否有条件注解在进行注册
 *     public void processGroupImports() {
 * 			for (DeferredImportSelectorGrouping grouping : this.groupings.values()) {
 * 				Predicate<String> exclusionFilter = grouping.getCandidateFilter();
 * 				grouping.getImports().forEach(entry -> {
 * 					ConfigurationClass configurationClass = this.configurationClasses.get(entry.getMetadata());
 * 					try {
 * 						processImports(configurationClass, asSourceClass(configurationClass, exclusionFilter),
 * 								Collections.singleton(asSourceClass(entry.getImportClassName(), exclusionFilter)),
 * 								exclusionFilter, false);
 *                                        }
 * 					catch (BeanDefinitionStoreException ex) {
 * 						throw ex;
 *                    }
 * 					catch (Throwable ex) {
 * 						throw new BeanDefinitionStoreException(
 * 								"Failed to process import candidates for configuration class [" +
 * 										configurationClass.getMetadata().getClassName() + "]", ex);
 *                    }                * 				}            );
 * 			}
 * 		}
 * </code>
 */
@SpringBootApplication
@Slf4j
@EnableAutoConfigThreadPool
@PropertySource("classpath:db.properties")
public class SpringComponentsLightweightTestSampleApplication implements CommandLineRunner {

    @Value("127.0.0.1")
    private String url;

    @Autowired
    private ServiceBean serviceBean;
    public static void main(String[] args) {
        SpringApplication.run(SpringComponentsLightweightTestSampleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        //test dubbo service bean inject ref
        log.info("service bean ref: {} hello: {}",serviceBean.refName(),url);
    }
}
