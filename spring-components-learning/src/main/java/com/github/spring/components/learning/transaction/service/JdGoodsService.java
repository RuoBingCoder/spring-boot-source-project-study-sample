package com.github.spring.components.learning.transaction.service;


import com.github.spring.components.learning.transaction.pojo.JdGoods;

import java.util.List;

public interface JdGoodsService{


    int deleteByPrimaryKey(Integer id);

    int insert(JdGoods record);

    int insertSelective(JdGoods record);

    JdGoods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JdGoods record);

    int updateByPrimaryKey(JdGoods record);

    int updateBatch(List<JdGoods> list);

    int updateBatchSelective(List<JdGoods> list);

    int batchInsert(List<JdGoods> list);

}
