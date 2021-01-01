package com.sjl.spring.components.transaction.service;

import com.sjl.spring.components.transaction.pojo.HeroDo;

import java.util.List;

public interface HeroService{


    int insert(HeroDo record);

    int insertSelective(HeroDo record);


    String init();


    List<HeroDo> selectList(HeroDo hero);

    int delete(HeroDo entity);

    int updateBySelective(HeroDo hero, HeroDo whereHero);

}
