package com.github.spring.components.learning.transaction.service;


import com.github.spring.components.learning.transaction.pojo.HeroDo;

import java.util.List;

public interface HeroService{


    int insert(HeroDo record);

    int insertSelective(HeroDo record);


    String init();


    List<HeroDo> selectList(HeroDo hero);

    int delete(HeroDo entity);

    int updateBySelective(HeroDo hero, HeroDo whereHero);

}
