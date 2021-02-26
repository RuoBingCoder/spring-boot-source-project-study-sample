package config;

import common.annotation.EnableAutoConfigThreadPool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import helper.ThreadPoolHelper;

/**
 * @author jianlei.shi
 * @date 2021/2/21 3:56 下午
 * @description ThreadPoolConfig
 * @see org.springframework.context.annotation.ConfigurationClassPostProcessor
 * <code>
 *     项目类BeanDefinitions优先级->配置包括SPI配置类BeanDefinitions优先级
 *   public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
 *   //....省略部分代码
 *     Set<BeanDefinitionHolder> candidates = new LinkedHashSet<>(configCandidates);
 * 		Set<ConfigurationClass> alreadyParsed = new HashSet<>(configCandidates.size());
 * 		do {
 * 			parser.parse(candidates); //解析注解
 * 			parser.validate();
 *
 * 			Set<ConfigurationClass> configClasses = new LinkedHashSet<>(parser.getConfigurationClasses());
 * 			configClasses.removeAll(alreadyParsed);
 *
 * 			// Read the model and create bean definitions based on its content
 * 			if (this.reader == null) {
 * 				this.reader = new ConfigurationClassBeanDefinitionReader(
 * 						registry, this.sourceExtractor, this.resourceLoader, this.environment,
 * 						this.importBeanNameGenerator, parser.getImportRegistry());
 *                        }
 * 			this.reader.loadBeanDefinitions(configClasses); //加载配置类BeanDefinitions
 * 			alreadyParsed.addAll(configClasses);
 * 		}
 * 	}
 * </code>
 * <code>
 *
 *     @ConditionalOnBean
 * @see org.springframework.context.annotation.ConfigurationClassParser
 *
 * 	protected void processConfigurationClass(ConfigurationClass configClass, Predicate<String> filter) throws IOException {
 * 		if (this.conditionEvaluator.shouldSkip(configClass.getMetadata(), ConfigurationPhase.PARSE_CONFIGURATION)) { //计算配置类即@Configuration 条件是否满足不满足则跳过
 * 			return;
 *                }
 *
 * 		//....
 *
 *
 * </code>
 */
@EnableConfigurationProperties(ThreadPoolPropertiesConfig.class)
@Configuration
@ConditionalOnBean(annotation = EnableAutoConfigThreadPool.class)
public class ThreadPoolConfig {

    @Value("${simple.thread.pool.corePoolSize}")
    private Integer coreSizeValue;

    @Value("${simple.thread.pool.maxPoolSize}")
    private Integer maxSizeValue;

    @Value("${simple.thread.pool.keepAliveTime}")
    private Long keepTimeValue;

    @Value("${simple.thread.pool.initialDelay}")
    private Long initialDelayValue;

    @Value("${simple.thread.pool.period}")
    private Long periodValue;

    @Value("${simple.thread.pool.delay}")
    private Long delayValue;

    @Bean
    public ThreadPoolHelper threadPoolHelper() {
        return new ThreadPoolHelper(coreSizeValue, maxSizeValue, keepTimeValue, initialDelayValue, periodValue, delayValue);
    }
}
