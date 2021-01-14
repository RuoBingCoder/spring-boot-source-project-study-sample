package com.github.spring.components.transaction.dao;

import com.github.spring.components.transaction.pojo.JdGoods;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface JdGoodsMapper  {
    int deleteByPrimaryKey(Integer id);

    int insert(JdGoods record);

    int insertSelective(JdGoods record);

    JdGoods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JdGoods record);

    int updateByPrimaryKey(JdGoods record);

    int updateBatch(List<JdGoods> list);

    int updateBatchSelective(List<JdGoods> list);

    int batchInsert(@Param("list") List<JdGoods> list);
}