package com.sjl.spring.components.transaction.service.impl;

import com.sjl.spring.components.transaction.service.TeamService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.sjl.spring.components.transaction.dao.TeamMapper;
import java.util.List;
import com.sjl.spring.components.transaction.pojo.Team;

@Service
public class TeamServiceImpl implements TeamService {

    @Resource
    private TeamMapper teamMapper;

    @Override
    public int deleteByPrimaryKey(Integer teamId) {
        return teamMapper.deleteByPrimaryKey(teamId);
    }

    @Override
    public int insert(Team record) {
        return teamMapper.insert(record);
    }

    @Override
    public int insertSelective(Team record) {
        return teamMapper.insertSelective(record);
    }

    @Override
    public Team selectByPrimaryKey(Integer teamId) {
        return teamMapper.selectByPrimaryKey(teamId);
    }

    @Override
    public int updateByPrimaryKeySelective(Team record) {
        return teamMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Team record) {
        return teamMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateBatch(List<Team> list) {
        return teamMapper.updateBatch(list);
    }

    @Override
    public int updateBatchSelective(List<Team> list) {
        return teamMapper.updateBatchSelective(list);
    }

    @Override
    public int batchInsert(List<Team> list) {
        return teamMapper.batchInsert(list);
    }

}
