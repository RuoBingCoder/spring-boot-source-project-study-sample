package com.sjl.spring.components;

import com.alibaba.fastjson.JSONObject;
import com.sjl.spring.components.transaction.dao.HeroMapper;
import com.sjl.spring.components.transaction.dao.JdGoodsMapper;
import com.sjl.spring.components.transaction.pojo.HeroDo;
import com.sjl.spring.components.transaction.pojo.JdGoods;
import com.sjl.spring.components.transaction.pojo.TeamDo;
import com.sjl.spring.components.transaction.service.CommonOperateService;
import com.sjl.spring.components.transaction.service.HeroService;
import com.sjl.spring.components.transaction.service.JdGoodsService;
import com.sjl.spring.components.transaction.service.TeamService;
import com.sjl.spring.components.utils.DateUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.annotation.Resource;

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
    private CommonOperateService<TeamDo, HeroDo> commonOperateService;


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

    private HeroDo getHero() {
        return HeroDo.builder().createTime(DateUtils.getCurrentTime())
                .money(1000).name("安琪拉").type("法师").build();

    }

    private TeamDo getTeam() {
        return TeamDo.builder().createTime(DateUtils.getCurrentTime())
                .teamName("斗地主").updateTime(DateUtils.getCurrentTime())
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
        HeroDo hero = HeroDo.builder().name("周瑜").build();
//        List<Hero> heroes = heroMapper.selectByAll(hero);
//        System.out.println("====> heroes :"+JSONObject.toJSONString(heroes));
    }


}
