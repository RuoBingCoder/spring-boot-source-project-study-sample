package com.github.spring.components.transaction.service.impl;

import com.github.spring.components.mybatis.common.wrapper.QueryWrapper;
import com.github.spring.components.transaction.pojo.TeamDo;
import com.github.spring.components.transaction.dao.TeamMapper;
import com.github.spring.components.transaction.service.TeamService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * JdkDynamicAopProxy
 *
 */
@Service
public class TeamServiceImpl implements TeamService {

    @Resource
    private TeamMapper teamMapper;

//    @Resource
//    private HeroService heroService;

    @Override
    public int deleteByPrimaryKey(Integer teamId) {
//        return teamMapper.deleteByPrimaryKey(teamId);
        return 1;
    }

    /**
     * 测试事务
     * {JdkDynamicAopProxy #getProxy()}
     * AopContext.setCurrentProxy(proxy);
     * @param record
     * @return 1.
     */
    @Override
//    @Transactional(propagation = Propagation.REQUIRED)
    public int insert(TeamDo record) {
        //不捕获异常 两个事务都回滚
        //方法hero insert 捕获异常 只有hero insert 进行回滚 前提 team insert 不捕获异常
        teamMapper.insert(record);
//        Hero hero = Hero.builder().createTime(LocalDateTime.now()).money(20).name("百里守约").build();
//        //测试非事务方法调用事务方法获取代理对象
//        HeroServiceImpl heroService = (HeroServiceImpl) AopContext.currentProxy();
//        heroService.insert(hero);
        return 1;
    }

    @Override
    public int insertSelective(TeamDo record) {
        return 1;
    }

    @Override
    public TeamDo selectByPrimaryKey(Integer teamId) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(TeamDo record) {
        return 1;
    }

    @Override
    public int updateByPrimaryKey(TeamDo record) {
        return 1;
    }

    @Override
    public int updateBatch(List<TeamDo> list) {
        return 1;
    }

    @Override
    public int updateBatchSelective(List<TeamDo> list) {
        return 1;
    }

    @Override
    public int batchInsert(List<TeamDo> list) {
        return 1;
    }

    @Override
    public int update(TeamDo team, TeamDo whereTeam) {
        QueryWrapper<TeamDo> teamQuery=new QueryWrapper<>();
        teamQuery.setParams(whereTeam);
        return teamMapper.updateBySelective(team,teamQuery);
    }

    @Override
    public List<TeamDo> selTeamList(TeamDo team) {
        QueryWrapper<TeamDo> teamQuery=new QueryWrapper<>();
        teamQuery.setParams(team);
        return teamMapper.selectList(teamQuery);
    }

}
