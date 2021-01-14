//package com.sjl.spring.components.transaction.service.impl;
//
//import com.sjl.spring.components.transaction.pojo.Hero;
//import com.sjl.spring.components.transaction.pojo.Team;
//import com.sjl.spring.components.transaction.service.CommonOperateService;
//import com.sjl.spring.components.transaction.service.HeroService;
//import com.sjl.spring.components.transaction.service.TeamService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.TransactionDefinition;
//import org.springframework.transaction.TransactionStatus;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.transaction.support.DefaultTransactionDefinition;
//
//import javax.annotation.Resource;
//import java.util.List;
//
///**
// * @author: JianLei
// * @date: 2020/11/7 8:13 下午
// * @description: 编程式提交事务
// */
////@Service("commonOperateServiceProgram")
//@Slf4j
//public class ProgramOperateServiceImpl implements CommonOperateService<Team, Hero> {
//    @Resource
//    private HeroService heroService;
//
//    @Resource
//    private TeamService teamService;
//
//    @Resource
//    private DataSourceTransactionManager transactionManager;
//
//
//    @Override
//    public Integer operation(Team team, Hero hero) {
//        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
//        definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//        TransactionStatus status = transactionManager.getTransaction(definition);
//        try {
//            teamService.insert(team);
//            insertHero(hero);
//            transactionManager.commit(status);
//        } catch (Exception e) {
//            log.error("操作添加出现异常", e);
//            transactionManager.rollback(status);
//            throw new RuntimeException("操作添加出现异常");
//
//        }
//
//        return null;
//    }
//
//    public void insertHero(Hero hero) {
//        try {
//            heroService.insert(hero);
//            List<String> list = null;
//            list.add("abc");
//        } catch (Exception e) {
//            throw new RuntimeException("保存英雄出现异常");
//
//        }
//    }
//}
