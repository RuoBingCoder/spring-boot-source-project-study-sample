package com.sjl.spring.components;

import com.alibaba.fastjson.JSONObject;
import com.sjl.spring.components.transaction.dao.HeroMapper;
import com.sjl.spring.components.transaction.dao.JdGoodsMapper;
import com.sjl.spring.components.transaction.pojo.Hero;
import com.sjl.spring.components.transaction.pojo.JdGoods;
import com.sjl.spring.components.transaction.pojo.Team;
import com.sjl.spring.components.transaction.service.CommonOperateService;
import com.sjl.spring.components.transaction.service.HeroService;
import com.sjl.spring.components.transaction.service.JdGoodsService;
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


    @Resource
    private HeroMapper heroMapper;

    @Resource
    private HeroService heroService;
    @Test
    void contextLoads() {
        JdGoods jdGoods = JdGoods.builder().operate("insert").rate("14").shopName("森马_2").thumbnail("http://www.baidu.com").title("测试_2").build();
        int insert = jdGoodsService.insert(jdGoods);
        System.out.println("====>>" + insert);
    }

    /**
     * @see HeroMapper
     */
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

    @Test
    public void daoSupportQueryTest() throws InstantiationException, IllegalAccessException {

    }

    @Test
    public void heroInsertTest(){
//        heroService.insert(Hero.builder().createTime(LocalDateTime.now()).money(100).name("马可波罗").build());
//        heroService.init();
//        Hero hero = heroMapper.selectById(1);
//        System.out.println("==>select hero :"+JSONObject.toJSONString(hero));
        Hero hero = Hero.builder().name("周瑜").build();
//        List<Hero> heroes = heroMapper.selectByAll(hero);
//        System.out.println("====> heroes :"+JSONObject.toJSONString(heroes));
    }


}
