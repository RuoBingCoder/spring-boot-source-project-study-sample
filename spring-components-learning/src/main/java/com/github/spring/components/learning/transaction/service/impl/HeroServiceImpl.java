package com.github.spring.components.learning.transaction.service.impl;

import com.github.spring.components.learning.enhancemybatis.common.wrapper.QueryWrapper;
import com.github.spring.components.learning.transaction.dao.HeroMapper;
import com.github.spring.components.learning.transaction.pojo.HeroDo;
import com.github.spring.components.learning.transaction.service.HeroService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.List;


/**
 * @see org.springframework.transaction.annotation.SpringTransactionAnnotationParser 解析 {@link Transactional}
 * @see #CglibAopProxy DynamicAdvisedInterceptor#intercept}
 * 有{@link Transactional} 则走{@link CglibAopProxy#CglibMethodInvocation#proceed()}
 */

@Service
public class HeroServiceImpl implements HeroService {

    @Resource
    private HeroMapper heroMapper;

    /**
     * @see org.springframework.aop.framework.CglibAopProxy.DynamicAdvisedInterceptor#advised->BeanFactoryTransactionAttributeSourceAdvisor
     * @see org.springframework.aop.framework.AdvisedSupport#getInterceptorsAndDynamicInterceptionAdvice
     * @see org.springframework.aop.framework.DefaultAdvisorChainFactory#getInterceptorsAndDynamicInterceptionAdvice(Advised, Method, Class)
     * @see org.springframework.transaction.interceptor.TransactionAttributeSourcePointcut#matches(Method, Class)
     * @see org.springframework.transaction.interceptor.AbstractFallbackTransactionAttributeSource#getTransactionAttribute(Method, Class)
     * @see org.springframework.transaction.interceptor.AbstractFallbackTransactionAttributeSource#computeTransactionAttribute(Method, Class) //method 非 public 直接返回null
     * @see org.springframework.transaction.annotation.AnnotationTransactionAttributeSource#findTransactionAttribute(Method)  //find @Transactional注解获取属性
     * 最后 	this.attributeCache.put(cacheKey, txAttr);
     * 调用方法时会从attributeCache 获取没有则走普通方法
     * 如果获取chain 为空,则进行普通处理
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insert(HeroDo record) {
        heroMapper.insert(record);
       /* try {
            heroMapper.insert(record);
        } catch (Exception e) {
            throw new RuntimeException(" hero insert exception!");
        }*/
        return 1;
    }

    @Override
    public int insertSelective(HeroDo record) {
        return heroMapper.insertSelective(record);
    }

    @Override
    public String init() {
        return "init test";
    }

    @Override
    public List<HeroDo> selectList(HeroDo hero) {
        QueryWrapper<HeroDo> heroQuery = new QueryWrapper<>();
        heroQuery.setParams(hero);
        return heroMapper.selectList(heroQuery);
    }

    @Override
    public int delete(HeroDo entity) {
        return heroMapper.delete(entity);
    }

    @Override
    public int updateBySelective(HeroDo hero, HeroDo whereHero) {
        QueryWrapper<HeroDo> heroQuery = new QueryWrapper<>();
        heroQuery.setParams(whereHero);
        return heroMapper.updateBySelective(hero, heroQuery);
    }


}
