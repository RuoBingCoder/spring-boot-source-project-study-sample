package com.sjl.spring.components.transaction.dao;
import com.sjl.spring.components.mybatis.common.mapper.SimpleBaseMapper;
import com.sjl.spring.components.transaction.pojo.Team;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeamMapper extends SimpleBaseMapper<Team,Team> {
    int deleteByPrimaryKey(Integer teamId);

//    int insert(Team record);

    int insertSelective(Team record);

    Team selectByPrimaryKey(Integer teamId);

    int updateByPrimaryKeySelective(Team record);

    int updateByPrimaryKey(Team record);

    int updateBatch(List<Team> list);

    int updateBatchSelective(List<Team> list);

    int batchInsert(@Param("list") List<Team> list);

    List<Team> selectByAll(Team team);


}