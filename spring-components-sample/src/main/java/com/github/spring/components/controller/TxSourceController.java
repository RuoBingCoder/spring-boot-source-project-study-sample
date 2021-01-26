package com.github.spring.components.controller;

import com.github.spring.components.transaction.pojo.TeamDo;
import com.github.spring.components.transaction.pojo.HeroDo;
import com.github.spring.components.transaction.service.HeroService;
import com.github.spring.components.transaction.service.TeamService;
import com.github.spring.components.utils.DateUtils;
import http.ModelResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: JianLei
 * @date: 2020/11/30 下午7:35
 * @description: TxSourceController
 */
@RestController
@RequestMapping("/tx")
@Slf4j
public class TxSourceController {


    @Resource
    private HeroService heroService;


    @Resource
    private TeamService teamService;

    /**
     * 得到的英雄
     *
     * @param hero 英雄
     * @return {@link ModelResult<List<HeroDo>> }
     * @author jianlei.shi
     * @date 2021-01-25 17:02:36
     */
    @PostMapping(value = "/getHero")
    public ModelResult<List<HeroDo>> getHero(@RequestBody HeroDo hero) {
        List<HeroDo> heroes = heroService.selectList(hero);
        return ModelResult.success(heroes);
    }

    /**
     * 删除的英雄
     *
     * @param hero 英雄
     * @return {@link ModelResult<String> }
     * @author jianlei.shi
     * @date 2021-01-25 17:02:40
     */
    @PostMapping("/deleteHero")
    public ModelResult<String> deleteHero(@RequestBody HeroDo hero) {
        int delete = heroService.delete(hero);
        if (delete > 0) {
            return ModelResult.success();
        }
        return ModelResult.error("删除失败!");
    }

    /**
     * 得到团队
     *
     * @param team 团队
     * @return {@link ModelResult<List<TeamDo>> }
     * @author jianlei.shi
     * @date 2021-01-25 17:02:43
     */
    @PostMapping(value = "/getTeam")
    public ModelResult<List<TeamDo>> getTeam(@RequestBody TeamDo team) {
        List<TeamDo> teams = teamService.selTeamList(team);
        return ModelResult.success(teams);


    }

    /**
     * 更新团队
     *
     * @param team 团队
     * @return {@link ModelResult<Integer> }
     * @author jianlei.shi
     * @date 2021-01-25 17:02:48
     */
    @PostMapping(value = "/updateTeam")
    public ModelResult<Integer> updateTeam(@RequestBody TeamDo team) {
        TeamDo where = TeamDo.builder().id(team.getId()).build();
        int update = teamService.update(team, where);
        return ModelResult.success(update);


    }

    /**
     * 更新的英雄
     *
     * @param hero 英雄
     * @return {@link ModelResult<Integer> }
     * @author jianlei.shi
     * @date 2021-01-25 17:02:54
     */
    @PostMapping(value = "/updateHero")
    public ModelResult<Integer> updateHero(@RequestBody HeroDo hero) {
        HeroDo build = HeroDo.builder().type(hero.getType()).build();
        int i = heroService.updateBySelective(hero, build);
        return ModelResult.success(i);


    }

    /**
     * 插入的团队
     *
     * @param team 团队
     * @return {@link ModelResult<Integer> }
     * @author jianlei.shi
     * @date 2021-01-25 17:02:56
     */
    @PostMapping(value = "/insertTeam")
    public ModelResult<Integer> insertTeam(@RequestBody TeamDo team) {
        team.setCreateTime(DateUtils.getCurrentTime());
        team.setUpdateTime(DateUtils.getCurrentTime());
        int i = teamService.insert(team);
        return ModelResult.success(i);


    }
}
