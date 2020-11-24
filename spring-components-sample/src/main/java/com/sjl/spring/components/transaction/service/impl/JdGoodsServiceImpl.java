package com.sjl.spring.components.transaction.service.impl;

import com.sjl.spring.components.transaction.pojo.Hero;
import com.sjl.spring.components.transaction.service.HeroService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import com.sjl.spring.components.transaction.dao.JdGoodsMapper;
import com.sjl.spring.components.transaction.pojo.JdGoods;
import com.sjl.spring.components.transaction.service.JdGoodsService;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JdGoodsServiceImpl implements JdGoodsService{

    @Resource
    private JdGoodsMapper jdGoodsMapper;

    @Resource
    private HeroService heroService;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return jdGoodsMapper.deleteByPrimaryKey(id);
    }

    /**
     * 测试事务
     * @param record
     * @return
     */
    @Override
    public int insert(JdGoods record) {
        jdGoodsMapper.insert(record);
        Hero hero = Hero.builder().createTime(LocalDateTime.now()).money(20).name("孙悟空").type("法师").build();
        heroService.insert(hero);
        return 1;
    }

    @Override
    public int insertSelective(JdGoods record) {
        return jdGoodsMapper.insertSelective(record);
    }

    @Override
    public JdGoods selectByPrimaryKey(Integer id) {
        return jdGoodsMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(JdGoods record) {
        return jdGoodsMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(JdGoods record) {
        return jdGoodsMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateBatch(List<JdGoods> list) {
        return jdGoodsMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<JdGoods> list) {
        return jdGoodsMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<JdGoods> list) {
        return jdGoodsMapper.batchInsert(list);
    }

}
