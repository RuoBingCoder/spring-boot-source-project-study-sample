package com.sjl.spring.components;

import com.alibaba.fastjson.JSONObject;
import com.sjl.spring.components.transaction.dao.JdGoodsMapper;
import com.sjl.spring.components.transaction.pojo.Hero;
import com.sjl.spring.components.transaction.pojo.Team;
import com.sjl.spring.components.transaction.service.CommonOperateService;
import com.sjl.spring.components.transaction.service.JdGoodsService;
import com.sjl.spring.components.transaction.pojo.JdGoods;
import com.sjl.spring.components.transaction.service.TeamService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@SpringBootTest
@EnableAspectJAutoProxy(proxyTargetClass = true,exposeProxy = true)
class SpringComponentsApplicationTests {


    @Autowired
    private JdGoodsService jdGoodsService;
    @Resource
    private JdGoodsMapper jdGoodsMapper;
    @Resource
    private TeamService teamService;
    @Autowired
    private CommonOperateService<Team, Hero> commonOperateService;

    @Test
    void contextLoads() {
        JdGoods jdGoods = JdGoods.builder().operate("insert").rate("12").shopName("森马").thumbnail("http://www.baidu.com").title("测试").build();
        int insert = jdGoodsService.insert(jdGoods);
        System.out.println("====>>" + insert);
    }

    @Test
    void queryTest() {
        JdGoods jdGoods = jdGoodsMapper.selectByPrimaryKey(2);
        System.out.println("===>" + JSONObject.toJSONString(jdGoods));

    }

    @Test
    void commonServiceTest() {
        commonOperateService.operation(getTeam(), getHero());
    }

    private Hero getHero() {
        return Hero.builder().createTime(LocalDateTime.now())
                .money(1000).name("安琪拉").type("法师").build();

    }

    private Team getTeam() {
        return Team.builder().createTime(LocalDateTime.now())
                .teamName("斗地主").updateTime(LocalDateTime.now())
                .build();
    }

    /**
     * 事务传播机制测试
     */
    @Test
    public void propagationAttrTest(){

        teamService.insert(getTeam());


    }


}
