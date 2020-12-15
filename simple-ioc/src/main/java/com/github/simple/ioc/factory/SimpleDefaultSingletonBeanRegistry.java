package com.github.simple.ioc.factory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: JianLei
 * @date: 2020/12/11 6:02 下午
 * @description: SimpleDefaultSingletonBeanRegistry
 */

public class SimpleDefaultSingletonBeanRegistry {


    /**
     * 一级缓存
     */
    protected final Map<String, Object> singletonObjectMap = new ConcurrentHashMap<>(256);
    /**
     * 三级缓存
     */
    protected final Map<String, SimpleObjectFactory<?>> singletonFactoryMap = new ConcurrentHashMap<>(256);
    /**
     * 二级缓存
     * {@link SimpleObjectFactory#getObject()} 主要是获取对象的时候,如果对象被代理了,则每次获取对象都会创建新的代理对象,二级缓存作用就是为了避免这种情况
     * 确保代理只被创建一次
     */
    protected final Map<String, Object> earlySingletonMap = new ConcurrentHashMap<>(256);

    /**
     * @param null
     * @return
     * @author jianlei.shi
     * @description 简单的往bean工厂添加bean
     * @date 10:01 下午 2020/12/11
     **/

    public <T> T getSingletonBean(String beanName, SimpleObjectFactory<?> objectFactory) throws Throwable {
        //此处省略了很多spring逻辑 只做简化处理
        Object object = objectFactory.getObject();
        addSingleton(beanName, object);
        return (T) object;
    }

    /**
     * 添加bean
     * @param beanName
     * @param singletonObject
     */
    private void addSingleton(String beanName, Object singletonObject) {
        synchronized (this.singletonObjectMap) {
            if (!this.singletonObjectMap.containsKey(beanName)) {
                this.singletonObjectMap.put(beanName, singletonObject);
                this.singletonFactoryMap.remove(beanName);
                this.earlySingletonMap.remove(beanName);
            }
        }
    }

    protected Object getSingleton(String beanName) throws Throwable {
        return getSingleton(beanName, true);
    }

    /**
     * 获取bean
     * @param beanName
     * @param isAllowRef
     * @return
     * @throws Throwable
     */
    protected Object getSingleton(String beanName, boolean isAllowRef) throws Throwable {
        Object singleton = singletonObjectMap.get(beanName);
        if (singleton == null) {
            Object obj = earlySingletonMap.get(beanName);
            if (obj == null) {
                SimpleObjectFactory<?> objectFactory = singletonFactoryMap.get(beanName);
                if (objectFactory != null && isAllowRef) {
                    singleton = objectFactory.getObject();
                    earlySingletonMap.put(beanName, singleton);
                    singletonFactoryMap.remove(beanName);
                }
            }

        }
        return singleton;
    }

}
