package com.sjl.spring.components.transaction.service;

import com.sjl.spring.components.transaction.pojo.Hero;
public interface HeroService{


    int insert(Hero record);

    int insertSelective(Hero record);

}
