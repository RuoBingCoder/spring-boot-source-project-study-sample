package com.github.simple.core.beans.factory;

import com.github.simple.core.annotation.*;
import com.github.simple.core.beans.factory.support.SimpleBeanFactorySupport;
import com.github.simple.core.config.SimpleConfigBean;
import com.github.simple.core.constant.SimpleIOCConstant;
import com.github.simple.core.definition.SimpleRootBeanDefinition;
import com.github.simple.core.enums.SimpleIOCEnum;
import com.github.simple.core.exception.SimpleBeanCreateException;
import com.github.simple.core.exception.SimpleBeanDefinitionNotFoundException;
import com.github.simple.core.exception.SimpleClassNotFoundException;
import com.github.simple.core.exception.SimpleClassTypeException;
import com.github.simple.core.utils.ClassUtils;
import com.github.simple.core.utils.ReflectUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author: JianLei
 * @date: 2020/12/11 5:29 下午
 * @description: AbsBeanFactory
 */
@Slf4j
public abstract class AbsBeanFactory extends SimpleDefaultSingletonBeanRegistry implements SimpleConfigBeanFactory {


    protected ClassLoader classLoader;
    /**
     * bean 定义
     */
    protected final Map<String, SimpleRootBeanDefinition> beanDefinitions = new ConcurrentHashMap<>(128);

    protected final List<String> beanDefinitionNames=new ArrayList<>();

    /**
     * bean后置处理器
     */
    protected final List<SimpleBeanPostProcessor> simplePostProcessors = new ArrayList<>(128);


    protected final List<SimpleBeanFactoryPostProcessor> beanFactoryPostProcessors=new ArrayList<>(128);

    @Override
    public <T> T getBean(Class<?> clazz) throws Throwable {
        return getBean(ClassUtils.toLowerBeanName(clazz.getSimpleName()));
    }



    /**
     * 获取bean
     *
     * @param name
     * @return
     * @throws Throwable
     */
    protected <T> T doGetBean(String name) throws Throwable {
        String beanName = transformName(name);
        //implements simpleFactoryBean call back!
        if (getFactoryObject(name) != null) {
            return (T) getFactoryBeanInnerInstance(getFactoryObject(name), beanName);
        }
        Object singleton = getSingleton(beanName);
        if (singleton != null) {
            return (T) singleton;
        }
        SimpleRootBeanDefinition sbf = getMergeBeanDefinition(beanName);
        Object bean;
        try {
            bean = getSingletonBean(beanName, () -> {
                        try {
                            return createBean(beanName, sbf);
                        } catch (Exception e) {
                            singletonObjectMap.clear();
                            throw new SimpleBeanCreateException(SimpleIOCEnum.CREATE_BEAN_ERROR.getMsg() + "[" + beanName + "]");
                        }
                    }
            );
        } catch (Exception e) {
            log.error("createBean exception!", e);
            throw new SimpleBeanCreateException("[" + beanName + "] getSingletonBean exception! errorMsg: [" + e.getMessage() + "]");
        }
        predictBeanType(bean);
        return (T) bean;
    }

    protected abstract void predictBeanType(Object beanName);

    protected abstract Object getFactoryBeanInnerInstance(Object singleton, String beanName);

    protected abstract Object getFactoryObject(String name);

    /**
     * @param beanName
     * @return beanDefinition
     * @description: 简单获取bean定义
     */
    private SimpleRootBeanDefinition getMergeBeanDefinition(String beanName) {
        try {
            SimpleRootBeanDefinition mbd = beanDefinitions.get(beanName);
            if (mbd == null) {
                throw new SimpleBeanDefinitionNotFoundException("the BeanDefinition not found:" + beanName);
            }
            return mbd;
        } catch (Exception e) {
            throw new SimpleBeanDefinitionNotFoundException("getMergeBeanDefinition exception!" + "[" + beanName + "]");
        }
    }

    protected abstract Object createBean(String beanName, SimpleRootBeanDefinition sbf) throws Throwable;

    /**
     * @param name
     * @return beanName
     * @description: 转换beanName
     */
    private String transformName(String name) {
        if (StringUtils.isBlank(name)) {
            throw new SimpleClassNotFoundException(SimpleIOCEnum.CLASS_TYPE_NOT_NULL.getMsg());
        }
        if (name.length() >= 1 && name.toCharArray()[0] >= 97 && name.toCharArray()[0] <= 122) {
            return name;
        }

        return ClassUtils.toLowerBeanName(name);
    }

    @Override
    public <T> T getBean(String name) throws Throwable {
        return doGetBean(name);
    }


    public void addBeanDefinition(String beanName, SimpleRootBeanDefinition rootBeanDefinition) {
        if (!this.beanDefinitions.containsKey(beanName)) {
            synchronized (this.beanDefinitions) {
                this.beanDefinitionNames.add(beanName);
                this.beanDefinitions.put(beanName, rootBeanDefinition);
            }
        }
    }

    /**
     * @param finalInstance
     * @param wrapperInstance
     * @return obj 代理对象&本对象
     */
    protected Object earlyRefSingleton(String beanName, Object wrapperInstance) {
        //spring 此处为依赖注入检查bean是否为代理类,如果是则替换为代理类实现依赖注入
        Object exportObject = wrapperInstance;
        for (SimpleBeanPostProcessor simpleBeanPostProcessor : getBeanPostProcessor()) {
            if (simpleBeanPostProcessor instanceof SimpleSmartInstantiationAwareBeanPostProcessor) {
                SimpleSmartInstantiationAwareBeanPostProcessor smart = (SimpleSmartInstantiationAwareBeanPostProcessor) simpleBeanPostProcessor;
                exportObject = smart.getEarlyBeanReference(wrapperInstance, beanName);
            }
        }
        return exportObject;
    }

    public void doRegistryBeanDefinition(Set<Class<?>> classSet) {
        SimpleRootBeanDefinition simpleRootBeanDefinitionSupport = buildRootBeanDefinition(SimpleBeanFactorySupport.class);
        addBeanDefinition(simpleRootBeanDefinitionSupport.getBeanName(),simpleRootBeanDefinitionSupport);
        registryPostProcessor();
        classSet.forEach(mbd -> {
            try {
                configBeanRegistry(mbd);
                generalRegistry(mbd);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }


    /**
     * @param null
     * @return
     * @author jianlei.shi
     * @description 配置bean注册 {@link SimpleConfig}
     * @date 5:20 下午 2020/12/17
     **/
    private void configBeanRegistry(Class<?> bdClazz) throws Throwable {
        if (ReflectUtils.matchAnnotationComponent(bdClazz, SimpleConfig.class)) {
            checkType(bdClazz, null);
            SimpleRootBeanDefinition configBeanDefinition = buildRootBeanDefinition(bdClazz);
            addBeanDefinition(configBeanDefinition.getBeanName(), configBeanDefinition);
            doParseAndRegistryConfigBean(bdClazz);
        }
    }

    private void checkType(Class<?> bdClazz, Method method) {
        if (bdClazz != null && ReflectUtils.checkClassModifier(bdClazz)) {
            throw new SimpleClassTypeException(bdClazz + ": class not public!->" + bdClazz.getSimpleName());
        }
        if (method != null && ReflectUtils.checkMethodModifier(method)) {
            throw new SimpleClassTypeException(method + ": method  not public!->" + method.getName());
        }
    }

    private void doParseAndRegistryConfigBean(Class<?> bdClazz) throws Throwable {
        Map<Method, SimpleBean> methodAndAnnotation = ReflectUtils.getMethodAndAnnotation(bdClazz, SimpleBean.class);
        SimpleRootBeanDefinition configMbd = buildRootBeanDefinition(SimpleConfigBean.class);
        if (!beanDefinitions.containsKey(configMbd.getBeanName())) {
            addBeanDefinition(configMbd.getBeanName(), configMbd);
        }
        for (Map.Entry<Method, SimpleBean> entry : methodAndAnnotation.entrySet()) {
            SimpleRootBeanDefinition configBeanDefinition;
            checkType(null, entry.getKey());
            if (!"".equals(entry.getValue().name())) {
                configBeanDefinition = buildRootBeanDefinition(entry.getKey().getReturnType(), entry.getValue().name(), true);
            } else {
                configBeanDefinition = buildRootBeanDefinition(entry.getKey().getReturnType());
            }
            addBeanDefinition(configBeanDefinition.getBeanName(), configBeanDefinition);
            SimpleConfigBean simpleBean = getBean(ClassUtils.toLowerBeanName(SimpleConfigBean.class.getSimpleName()));
            if (simpleBean != null) {
                if (!simpleBean.matchConfigClass(bdClazz)) {
                    SimpleBeanMethod method = new SimpleBeanMethod(createMethodMeta(entry.getKey()), bdClazz);
                    simpleBean.setBeanMethods(method);
                } else {
                    SimpleBeanMethod beanMethod = simpleBean.getBeanMethodByConfig(bdClazz);
                    beanMethod.getMethodMetadata().add(new SimpleMethodMeta(entry.getKey().getName(), entry.getKey()));

                }
            }
        }
    }


    private List<SimpleMethodMetadata> createMethodMeta(Method key) {
        List<SimpleMethodMetadata> methodMetadata = new ArrayList<>();
        SimpleMethodMetadata metadata = new SimpleMethodMeta(key.getName(), key);
        methodMetadata.add(metadata);
        return methodMetadata;
    }

    /**
     * @param null
     * @return
     * @author jianlei.shi
     * @description 普通bean注册
     * @date 4:27 下午 2020/12/17
     **/
    private void generalRegistry(Class<?> cb) {
        if (ReflectUtils.matchAnnotationComponent(cb, SimpleComponent.class) || ReflectUtils.matchAnnotationComponent(cb, SimpleService.class)) {
            SimpleRootBeanDefinition rootBeanDefinition = buildRootBeanDefinition(cb);
            addBeanDefinition(rootBeanDefinition.getBeanName(), rootBeanDefinition);
        }
    }

    /**
     * @param null
     * @return
     * @author jianlei.shi
     * @description 注册系统后置处理器
     * @date 4:28 下午 2020/12/17
     **/
    private void registryPostProcessor() {
        SimpleRootBeanDefinition autowiredBeanDefinition = buildRootBeanDefinition(SimpleAutowiredAnnotationBeanPostProcessor.class);
        addBeanDefinition(autowiredBeanDefinition.getBeanName(), autowiredBeanDefinition);


    }



    /**
     * @param null
     * @return
     * @author jianlei.shi
     * @description 处理 {@link SimpleAutowiredAnnotationBeanPostProcessor}
     * @date 4:29 下午 2020/12/17
     **/
    protected abstract void processInjectionBasedOnCurrentContext(List<SimpleBeanPostProcessor> sortedPostProcessors);

    public void sortPostProcessors(List<SimpleBeanPostProcessor> nonOrderPostprocessors, List<SimpleBeanPostProcessor> sortedPostProcessors) {
        Map<Integer, SimpleBeanPostProcessor> markPostProcessorMap = new LinkedHashMap<>();
        for (SimpleBeanPostProcessor nonOrderPostprocessor : nonOrderPostprocessors) {
            if (ClassUtils.matchOrdered(nonOrderPostprocessor)) {
                markPostProcessorMap.put(getOrdered(nonOrderPostprocessor), nonOrderPostprocessor);
            } else {
                markPostProcessorMap.put(SimpleIOCConstant.DEFAULT_ORDER_VALUE, nonOrderPostprocessor);
            }
        }
        List<SimpleBeanPostProcessor> sortedPostProcessorsList = markPostProcessorMap.entrySet().stream().sorted(Map.Entry.comparingByKey()).map(Map.Entry::getValue).collect(Collectors.toList());
        sortedPostProcessors.addAll(sortedPostProcessorsList);
    }

    private Integer getOrdered(SimpleBeanPostProcessor nonOrderPostprocessor) {
        return ClassUtils.getOrderedValue(nonOrderPostprocessor);
    }

    private SimpleRootBeanDefinition buildRootBeanDefinition(Class<?> clazz) {
//        if (ReflectUtils.isAssignableFrom(clazz, SimpleFactoryBean.class)) {
//            return buildRootBeanDefinition(clazz, ClassUtils.transformFactoryBeanName(clazz), true);
//        }
        return buildRootBeanDefinition(clazz, ClassUtils.toLowerBeanName(clazz.getSimpleName()), true);
    }

    private SimpleRootBeanDefinition buildRootBeanDefinition(Class<?> clazz, String beanName, Boolean isSingleton) {
        return SimpleRootBeanDefinition.builder().beanClass(clazz)
                .beanName(beanName)
                .isSingleton(isSingleton).build();
    }

    protected List<SimpleBeanPostProcessor> getBeanPostProcessor() {
        return this.simplePostProcessors;
    }

    public Map<String, SimpleRootBeanDefinition> getBeanDefinitions() {
        return beanDefinitions;
    }

    @Override
    public void registerBeanDefinition(String beanName, Object singletonObject) {
        this.beanDefinitions.put(beanName, (SimpleRootBeanDefinition) singletonObject);
    }

    public void destroyBeans() {
        simplePostProcessors.clear();
        earlySingletonMap.clear();
        singletonFactoryMap.clear();
        destroyFactoryBeanCache();
        beanDefinitions.clear();
    }

    protected abstract void destroyFactoryBeanCache();


    @Override
    public void addBeanPostProcessor(SimpleBeanPostProcessor beanPostProcessor) {
        simplePostProcessors.add(beanPostProcessor);
    }

    @Override
    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
}
