package com.sjl.spring.components.transaction.service.impl;

import com.sjl.spring.components.transaction.pojo.Hero;
import com.sjl.spring.components.transaction.service.HeroService;
import com.sjl.spring.components.transaction.service.TeamService;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import com.sjl.spring.components.transaction.dao.TeamMapper;

import java.time.LocalDateTime;
import java.util.List;

import com.sjl.spring.components.transaction.pojo.Team;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * JdkDynamicAopProxy
 *
 */
@Service
public class TeamServiceImpl implements TeamService {

    @Resource
    private TeamMapper teamMapper;

//    @Resource
//    private HeroService heroService;

    @Override
    public int deleteByPrimaryKey(Integer teamId) {
        return teamMapper.deleteByPrimaryKey(teamId);
    }

    /**
     * 测试事务
     * {JdkDynamicAopProxy #getProxy()}
     * AopContext.setCurrentProxy(proxy);
     * @param record
     * @return 1.
     */
    @Override
//    @Transactional(propagation = Propagation.REQUIRED)
    public int insert(Team record) {
        //不捕获异常 两个事务都回滚
        //方法hero insert 捕获异常 只有hero insert 进行回滚 前提 team insert 不捕获异常
        teamMapper.insert(record);
        Hero hero = Hero.builder().createTime(LocalDateTime.now()).money(20).name("百里守约").build();
        //测试非事务方法调用事务方法获取代理对象
        HeroServiceImpl heroService = (HeroServiceImpl) AopContext.currentProxy();
        heroService.insert(hero);
        return 1;
    }

    @Override
    public int insertSelective(Team record) {
        return teamMapper.insertSelective(record);
    }

    @Override
    public Team selectByPrimaryKey(Integer teamId) {
        return teamMapper.selectByPrimaryKey(teamId);
    }

    @Override
    public int updateByPrimaryKeySelective(Team record) {
        return teamMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Team record) {
        return teamMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateBatch(List<Team> list) {
        return teamMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<Team> list) {
        return teamMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<Team> list) {
        return teamMapper.batchInsert(list);
    }

}
