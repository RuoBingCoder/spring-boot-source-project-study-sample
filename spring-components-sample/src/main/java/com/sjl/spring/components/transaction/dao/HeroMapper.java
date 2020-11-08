package com.sjl.spring.components.transaction.dao;

import com.sjl.spring.components.transaction.pojo.Hero;

public interface HeroMapper {
    int insert(Hero record);

    int insertSelective(Hero record);
}