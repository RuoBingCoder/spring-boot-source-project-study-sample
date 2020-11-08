package com.sjl.spring.components.transaction.service;

import java.util.List;
import com.sjl.spring.components.transaction.pojo.Team;
public interface TeamService{


    int deleteByPrimaryKey(Integer teamId);

    int insert(Team record);

    int insertSelective(Team record);

    Team selectByPrimaryKey(Integer teamId);

    int updateByPrimaryKeySelective(Team record);

    int updateByPrimaryKey(Team record);

    int updateBatch(List<Team> list);

    int updateBatchSelective(List<Team> list);

    int batchInsert(List<Team> list);

}
