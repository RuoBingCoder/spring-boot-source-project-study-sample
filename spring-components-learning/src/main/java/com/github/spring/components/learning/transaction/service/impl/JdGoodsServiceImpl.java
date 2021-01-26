package com.github.spring.components.learning.transaction.service.impl;

import com.github.spring.components.learning.transaction.dao.JdGoodsMapper;
import com.github.spring.components.learning.transaction.pojo.HeroDo;
import com.github.spring.components.learning.transaction.pojo.JdGoods;
import com.github.spring.components.learning.transaction.service.HeroService;
import com.github.spring.components.learning.transaction.service.JdGoodsService;
import com.github.spring.components.learning.utils.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class JdGoodsServiceImpl implements JdGoodsService {

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
        HeroDo hero = HeroDo.builder().createTime(DateUtils.getCurrentTime()).money(20).name("孙悟空").type("法师").build();
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
