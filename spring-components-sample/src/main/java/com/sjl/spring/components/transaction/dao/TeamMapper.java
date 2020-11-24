package com.sjl.spring.components.transaction.dao;

import com.sjl.spring.components.transaction.pojo.Team;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TeamMapper {
    int deleteByPrimaryKey(Integer teamId);

    int insert(Team record);

    int insertSelective(Team record);

    Team selectByPrimaryKey(Integer teamId);

    int updateByPrimaryKeySelective(Team record);

    int updateByPrimaryKey(Team record);

    int updateBatch(List<Team> list);

    int updateBatchSelective(List<Team> list);

    int batchInsert(@Param("list") List<Team> list);
}