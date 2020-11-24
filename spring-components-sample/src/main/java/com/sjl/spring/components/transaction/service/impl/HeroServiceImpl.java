package com.sjl.spring.components.transaction.service.impl;

import com.sjl.spring.components.transaction.pojo.Team;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import com.sjl.spring.components.transaction.dao.HeroMapper;
import com.sjl.spring.components.transaction.pojo.Hero;
import com.sjl.spring.components.transaction.service.HeroService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HeroServiceImpl implements HeroService {

    @Resource
    private HeroMapper heroMapper;

    @Override
    @Transactional
    public int insert(Hero record) {
        heroMapper.insert(record);
       /* try {
            heroMapper.insert(record);
        } catch (Exception e) {
            throw new RuntimeException(" hero insert exception!");
        }*/
        return 1;
    }

    @Override
    public int insertSelective(Hero record) {
        return heroMapper.insertSelective(record);
    }


}
