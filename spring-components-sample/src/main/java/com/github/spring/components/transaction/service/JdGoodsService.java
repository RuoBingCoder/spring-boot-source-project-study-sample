package com.github.spring.components.transaction.service;

import java.util.List;
import com.github.spring.components.transaction.pojo.JdGoods;
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
