package com.sjl.spring.components.transaction.service.impl;

import com.sjl.spring.components.transaction.pojo.Team;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.sjl.spring.components.transaction.dao.HeroMapper;
import com.sjl.spring.components.transaction.pojo.Hero;
import com.sjl.spring.components.transaction.service.HeroService;
@Service
public class HeroServiceImpl implements HeroService{

    @Resource
    private HeroMapper heroMapper;

    @Override
    public int insert(Hero record) {
        return heroMapper.insert(record);
    }

    @Override
    public int insertSelective(Hero record) {
        return heroMapper.insertSelective(record);
    }




}
