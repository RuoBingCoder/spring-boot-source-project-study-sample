package com.github.enable.annotation;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Set;

/**
 * @author jianlei.shi
 * @date 2021/2/11 1:35 下午
 * @description SimpleImportSelect
 * @see org.springframework.context.annotation.ConfigurationClassPostProcessor#processConfigBeanDefinitions->
 * @see org.springframework.context.annotation.ConfigurationClassParser#parse(Set)
 * @see org.springframework.context.annotation.ConfigurationClassParser#processConfigurationClass
 * @see org.springframework.context.annotation.ConfigurationClassParser#doProcessConfigurationClass
 * @see org.springframework.context.annotation.ConfigurationClassParser#processImports
 */
public class SimpleImportSelect implements ImportSelector {

    /**
     * 选择进口
     *
     * @param importingClassMetadata 进口类元数据
     * @return {@link String[] }
     * @author jianlei.shi
     * @date 2021-02-11 14:13:09
     * <code>
     *
     *     private void processImports(ConfigurationClass configClass, SourceClass currentSourceClass, //currentSourceClass 启动类
     * 			Collection<SourceClass> importCandidates, Predicate<String> exclusionFilter,
     * 			boolean checkForCircularImports) {
     *
     * 		if (importCandidates.isEmpty()) {
     * 			return;
     *                }
     *
     * 		if (checkForCircularImports && isChainedImportOnStack(configClass)) {
     * 			this.problemReporter.error(new CircularImportProblem(configClass, this.importStack));
     *        }
     * 		else {
     * 			this.importStack.push(configClass);
     * 			try {
     * 				for (SourceClass candidate : importCandidates) {
     * 					if (candidate.isAssignable(ImportSelector.class)) {
     * 						// Candidate class is an ImportSelector -> delegate to it to determine imports
     * 						Class<?> candidateClass = candidate.loadClass();
     * 						ImportSelector selector = ParserStrategyUtils.instantiateClass(candidateClass, ImportSelector.class,
     * 								this.environment, this.resourceLoader, this.registry);
     * 						Predicate<String> selectorFilter = selector.getExclusionFilter();
     * 						if (selectorFilter != null) {
     * 							exclusionFilter = exclusionFilter.or(selectorFilter);
     *                        }
     * 						if (selector instanceof DeferredImportSelector) {
     * 							this.deferredImportSelectorHandler.handle(configClass, (DeferredImportSelector) selector);
     *                        }
     * 						else {
     * 							String[] importClassNames = selector.selectImports(currentSourceClass.getMetadata());
     * 							Collection<SourceClass> importSourceClasses = asSourceClasses(importClassNames, exclusionFilter);
     * 							processImports(configClass, currentSourceClass, importSourceClasses, exclusionFilter, false);
     *                        }
     *                    }
     * 					else if (candidate.isAssignable(ImportBeanDefinitionRegistrar.class)) {
     * 						// Candidate class is an ImportBeanDefinitionRegistrar ->
     * 						// delegate to it to register additional bean definitions
     * 						Class<?> candidateClass = candidate.loadClass();
     * 						ImportBeanDefinitionRegistrar registrar =
     * 								ParserStrategyUtils.instantiateClass(candidateClass, ImportBeanDefinitionRegistrar.class,
     * 										this.environment, this.resourceLoader, this.registry);
     * 						configClass.addImportBeanDefinitionRegistrar(registrar, currentSourceClass.getMetadata());
     *                    }
     * 					else {
     * 						// Candidate class not an ImportSelector or ImportBeanDefinitionRegistrar ->
     * 						// process it as an @Configuration class
     * 						this.importStack.registerImport(
     * 								currentSourceClass.getMetadata(), candidate.getMetadata().getClassName());
     * 						processConfigurationClass(candidate.asConfigClass(configClass), exclusionFilter);
     *                    }
     *                }
     *            }
     * 			catch (BeanDefinitionStoreException ex) {
     * 				throw ex;
     *            }
     * 			catch (Throwable ex) {
     * 				throw new BeanDefinitionStoreException(
     * 						"Failed to process import candidates for configuration class [" +
     * 						configClass.getMetadata().getClassName() + "]", ex);
     *            }
     * 			finally {
     * 				this.importStack.pop();
     *            }
     *        }    * 	}
     * </code>
     */
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        //返回的bean 都会被封装成ConfigClass  信息包装成 SourceClass
        return new String[]{"com.github.enable.SimpleSelectBean"};
    }
}
