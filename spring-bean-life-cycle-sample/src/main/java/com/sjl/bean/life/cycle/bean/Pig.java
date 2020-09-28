package com.sjl.bean.life.cycle.bean;

import com.sjl.bean.life.cycle.constants.Constant;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author: JianLei
 * @date: 2020/9/11 5:41 下午
 * @description:
 *  <p>1、->*************[InstantiationAwareDemo postProcessBeforeInstantiation 执行!]**************
 *     2、->开始执行Pig<无参>构造器
 *     3、->MergedBeanDefinitionPostProcessorImpl postProcessMergedBeanDefinition()
 *     4、->---------[InstantiationAwareDemo postProcessAfterInstantiation 执行! ]--------
 *     5、->============[InstantiationAwareDemo postProcessPropertyValues 执行! ]======================[]bean: Pig->beanName: pig
 *     6、->[BeanPostDemo postProcess[Before]Initialization 执行! ]
 *     7、->开始执行afterPropertiesSet
 *     8、->[BeanPostDemo postProcess[After]Initialization 执行! ]
 */
@Component
public class Pig implements InitializingBean, DisposableBean {

    private String name;

    public Pig(String name) {
    System.out.println(Constant.count.incrementAndGet()+"、->开始执行Pig<有参>构造器");
        this.name = name;

    }

    @PostConstruct
    public void testConstructor(){
    System.out.println(Constant.count.incrementAndGet()+"、->开始testConstructor");
    }
    public Pig() {
        System.out.println(Constant.count.incrementAndGet()+"、->开始执行Pig<无参>构造器");

    }

    @Override
    public void afterPropertiesSet() throws Exception {
    System.out.println(Constant.count.incrementAndGet()+"、->开始执行afterPropertiesSet");
    }

    @Override
    public void destroy() throws Exception {
    System.out.println(Constant.count.incrementAndGet()+"、->开始destroy()");
    }
}
