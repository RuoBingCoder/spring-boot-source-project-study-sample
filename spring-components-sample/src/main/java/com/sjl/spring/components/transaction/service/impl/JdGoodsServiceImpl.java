package com.sjl.spring.components.transaction.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.sjl.spring.components.transaction.dao.JdGoodsMapper;
import com.sjl.spring.components.transaction.pojo.JdGoods;
import com.sjl.spring.components.transaction.service.JdGoodsService;
@Service
public class JdGoodsServiceImpl implements JdGoodsService{

    @Resource
    private JdGoodsMapper jdGoodsMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return jdGoodsMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(JdGoods record) {
        return jdGoodsMapper.insert(record);
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
