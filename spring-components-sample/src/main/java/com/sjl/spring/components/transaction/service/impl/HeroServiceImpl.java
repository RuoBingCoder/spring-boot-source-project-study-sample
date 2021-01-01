package com.sjl.spring.components.transaction.service.impl;

import com.sjl.spring.components.mybatis.common.wrapper.QueryWrapper;
import com.sjl.spring.components.transaction.dao.HeroMapper;
import com.sjl.spring.components.transaction.pojo.HeroDo;
import com.sjl.spring.components.transaction.service.HeroService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * @see org.springframework.transaction.annotation.SpringTransactionAnnotationParser 解析 {@link Transactional}
 * @see  #CglibAopProxy DynamicAdvisedInterceptor#intercept}
 * 有{@link Transactional} 则走{@link #CglibAopProxy CglibMethodInvocation#proceed()}
 *
 *
 */

@Service
public class HeroServiceImpl implements HeroService {

    @Resource
    private HeroMapper heroMapper;

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
        QueryWrapper<HeroDo> heroQuery=new QueryWrapper<>();
        heroQuery.setParams(hero);
        return heroMapper.selectList(heroQuery);
    }

    @Override
    public int delete(HeroDo entity) {
        return heroMapper.delete(entity);
    }

    @Override
    public int updateBySelective(HeroDo hero, HeroDo whereHero) {
        QueryWrapper<HeroDo> heroQuery=new QueryWrapper<>();
        heroQuery.setParams(whereHero);
        return heroMapper.updateBySelective(hero,heroQuery);
    }


}
