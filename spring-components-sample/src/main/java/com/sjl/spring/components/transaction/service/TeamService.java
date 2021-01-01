package com.sjl.spring.components.transaction.service;

import com.sjl.spring.components.transaction.pojo.TeamDo;

import java.util.List;
public interface TeamService{


    int deleteByPrimaryKey(Integer teamId);

    int insert(TeamDo record);

    int insertSelective(TeamDo record);

    TeamDo selectByPrimaryKey(Integer teamId);

    int updateByPrimaryKeySelective(TeamDo record);

    int updateByPrimaryKey(TeamDo record);

    int updateBatch(List<TeamDo> list);

    int updateBatchSelective(List<TeamDo> list);

    int batchInsert(List<TeamDo> list);

    int update(TeamDo team, TeamDo whereTeam);

    List<TeamDo> selTeamList(TeamDo team);

}
