package com.sjl.spring.components;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sjl.spring.components.event.CustomEvent;
import com.sjl.spring.components.pojo.GeoHolder;
import com.sjl.spring.components.service.FactoryBeanService;
import com.sjl.spring.components.service.LazyService;
import com.sjl.spring.components.transaction.dao.HeroMapper;
import com.sjl.spring.components.transaction.dao.TeamMapper;
import com.sjl.spring.components.transaction.pojo.Hero;
import com.sjl.spring.components.transaction.pojo.Team;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.Order;
import org.springframework.retry.annotation.EnableRetry;

import javax.annotation.Resource;

@SpringBootApplication
@MapperScan("com.sjl.spring.components.transaction.dao")
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@Order
@EnableRetry
public class SpringComponentsApplication implements CommandLineRunner {
    /**
     * 推荐ObjectProvider 延迟注入 避免风险
     * {@link ObjectFactory#getObject()}
     */
    @Resource
    private ObjectProvider<GeoHolder> opGeoHolder;

    /**
     * @see org.springframework.context.annotation.CommonAnnotationBeanPostProcessor.ResourceElement#getResourceToInject(Object, String)
     * 创建代理
     */
    @Resource
    @Lazy
    private LazyService lazyService;

    @Resource
    private HeroMapper heroMapper;

    @Resource
    private TeamMapper teamMapper;


//@Resource
//private FactoryBeanService factoryBeanService;

    public static void main(String[] args) {
        //如果是容器启动之前注册事件则需要进行以下操作
      /*  =============================容器创建之前=====================================
        SpringApplication springApplication = new SpringApplication();
        springApplication.addListeners(new CustomApplicationListener());
        springApplication.run(SpringComponentsApplication.class,args);
        ================================结束==================================*/

        ConfigurableApplicationContext applicationContext = SpringApplication.run(SpringComponentsApplication.class, args);
        applicationContext.publishEvent(new CustomEvent("这是自定义事件"));
//        applicationContext.close();
        /**
         *
         * @see org.springframework.beans.factory.support.DefaultListableBeanFactory#resolveBean
         *
         *
         *
         */
        applicationContext.getBean(FactoryBeanService.class);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("====================CommandLineRunner========================");
        lazyService.testLazy();
//        ReflectUtil.threadClassLoader("");
        System.out.println("->>>>>>"+opGeoHolder.getIfAvailable());
        Hero hero = Hero.builder().name("周瑜").build();
//        List<Hero> heroes = heroMapper.queryHeroes(hero);
        Team team = Team.builder().teamName("亚特兰大老鹰").build();
        QueryWrapper<Hero> queryWrapper=new QueryWrapper(hero);
//        List<Hero> heroList = heroMapper.selectList(hero);
//        List<Hero> selectOne = heroMapper.queryHeroes(hero);
//        List<Team> teams = teamMapper.selectByAll(team);
//        List<Team> teams = teamMapper.selectList(team);
//        System.out.println("====> teams :"+ JSONObject.toJSONString(teams));


        Hero hero1 = Hero.builder().name("吕布").build();
        Hero where = Hero.builder().id(5).build();
//        int insert = heroMapper.insert(hero1);
//        System.out.println("===> res:"+insert);
        int i = heroMapper.updateBySelective(hero1,where);
//        int delete = heroMapper.delete(hero1);
        System.out.println("====>>update res:"+i);

    }
}
