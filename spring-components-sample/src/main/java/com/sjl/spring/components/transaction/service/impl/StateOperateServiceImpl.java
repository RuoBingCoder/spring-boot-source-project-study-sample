package com.sjl.spring.components.transaction.service.impl;

import com.sjl.spring.components.transaction.custom.annotation.EasyTransactional;
import com.sjl.spring.components.transaction.pojo.Hero;
import com.sjl.spring.components.transaction.pojo.Team;
import com.sjl.spring.components.transaction.service.CommonOperateService;
import com.sjl.spring.components.transaction.service.HeroService;
import com.sjl.spring.components.transaction.service.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author: JianLei
 * @date: 2020/11/7 2:13 下午
 * @description: 对接口 {@link CommonOperateService#operation(Object, Object)} 加 {@code #@EasyTransactional(rollbackFor = Exception.class)}注解开启事务
 */
@Service("com.sjl.spring.components.transaction.service.StateOperateServiceImpl")
@Slf4j
public class StateOperateServiceImpl implements CommonOperateService<Team, Hero> {
    @Resource
    private HeroService heroService;

    @Resource
    private TeamService teamService;

    @EasyTransactional(rollbackFor = Exception.class)
    @Override
    public Integer operation(Team team, Hero hero) {
        try {
            teamService.insert(team);
            insertHero(hero);
        } catch (Exception e) {
            log.error("保存游戏出现异常", e);
            throw new RuntimeException("保存游戏出现异常");
        }

        return null;
    }

    //    @Transactional(rollbackFor = Exception.class)
    public void insertHero(Hero hero) {
        try {
            heroService.insert(hero);
            List<String> list = null;
            list.add("abc");
        } catch (Exception e) {
            throw new RuntimeException("保存英雄出现异常");

        }
    }
}
