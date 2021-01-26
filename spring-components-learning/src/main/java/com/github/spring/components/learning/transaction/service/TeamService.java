package com.github.spring.components.learning.transaction.service;


import com.github.spring.components.learning.transaction.pojo.TeamDo;

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
