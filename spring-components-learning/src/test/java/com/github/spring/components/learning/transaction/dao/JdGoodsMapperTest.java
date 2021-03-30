package com.github.spring.components.learning.transaction.dao;

import com.alibaba.fastjson.JSONObject;
import com.github.spring.components.learning.transaction.pojo.JdGoods;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

/**
 * @author jianlei.shi
 * @date 2021/3/12 2:12 下午
 * @description JdGoodsMapperTest
 * @see LruCache 二级缓存的类
 */

public class JdGoodsMapperTest {
    private static JdGoodsMapper mapper;

   /* @BeforeClass
    public static void setUpMybatisDatabase() {
        SqlSessionFactory builder = new SqlSessionFactoryBuilder().build(JdGoodsMapperTest.class.getClassLoader().getResourceAsStream("mybatisTestConfiguration/JdGoodsMapperTestConfiguration.xml"));
        //you can use builder.openSession(false) to not commit to database
         SqlSession sqlSession1 = builder.openSession();
         SqlSession sqlSession2 = builder.openSession();
        final JdGoodsMapper mapper1 = sqlSession1.getMapper(JdGoodsMapper.class);


    }*/

    /**
     * 测试查询和更新缓存
     *
     * @return
     * @author jianlei.shi
     * @date 2021-03-12 14:52:12
     */
    @Test
    public void testQueryAndUpdateSecondCache() {
        SqlSessionFactory builder = new SqlSessionFactoryBuilder().build(JdGoodsMapperTest.class.getClassLoader().getResourceAsStream("mybatisTestConfiguration/JdGoodsMapperTestConfiguration.xml"));
        //you can use builder.openSession(false) to not commit to database
        SqlSession sqlSession1 = builder.openSession(true);
        SqlSession sqlSession2 = builder.openSession(true);
        SqlSession sqlSession3 = builder.openSession(true);
     /* |----------------------------------------------------------------------------|
        |final JdGoodsMapper mapper1 = sqlSession1.getMapper(JdGoodsMapper.class);   |
        |final JdGoodsMapper mapper2 = sqlSession2.getMapper(JdGoodsMapper.class);   | 一级缓存每个会话会保存各自的缓存不会共享
        |         mapper1.selectByPrimaryKey(1);                                     |
        |  mapper2.selectByPrimaryKey(1);                                            |
        |----------------------------------------------------------------------------|*/
        final JdGoodsMapper mapper1 = sqlSession1.getMapper(JdGoodsMapper.class);
        final JdGoodsMapper mapper2 = sqlSession2.getMapper(JdGoodsMapper.class);
        final JdGoodsMapper mapper3 = sqlSession3.getMapper(JdGoodsMapper.class);
        final JdGoods j1 = mapper1.selectByPrimaryKey(1);
        System.out.println("j1 读取数据:"+ JSONObject.toJSONString(j1));
        sqlSession1.commit();
        final JdGoods j2 = mapper2.selectByPrimaryKey(1);
        System.out.println("j2 读取数据:"+ JSONObject.toJSONString(j2));
        JdGoods jdGoods=new JdGoods();
        jdGoods.setId(1);
        jdGoods.setTitle("Test 联想(Lenovo)小新Pro13.3英寸全面屏超轻薄笔记本电脑(标压锐龙R5-3550H 16G 512G 2.5K QHD 100%sRGB)黑色");
        jdGoods.setTitleLink("https://item.jd.com/100005171461.html");
        jdGoods.setThumbnail("https://img11.360buyimg.com/n7/jfs/t1/99733/2/8261/174001/5e045083Ec79b2c6e/fac2d957b511c1da.jpg");
        jdGoods.setRate("已有39万+人评价");
        jdGoods.setShopName("联想京东自营旗舰店");
        jdGoods.setOperate("加入购物车");
        mapper3.updateByPrimaryKey(jdGoods);
        sqlSession3.commit(); //clear class$LruCache keyMap
        mapper2.selectByPrimaryKey(1);
//        final JdGoods j2 = mapper2.selectByPrimaryKey(1);
        System.out.println("j2 读取数据:"+ JSONObject.toJSONString(mapper2.selectByPrimaryKey(1)));
    }

    /**
     * 测试第一个缓存
     *
     * @return
     * @author jianlei.shi
     * @date 2021-03-12 14:52:17
     */
    @Test
    public void testFirstCache() {
        SqlSessionFactory builder = new SqlSessionFactoryBuilder().build(JdGoodsMapperTest.class.getClassLoader().getResourceAsStream("mybatisTestConfiguration/JdGoodsMapperTestConfiguration.xml"));
        //you can use builder.openSession(false) to not commit to database
        SqlSession sqlSession1 = builder.openSession(true);
        SqlSession sqlSession2 = builder.openSession(true);
     /* |----------------------------------------------------------------------------|
        |final JdGoodsMapper mapper1 = sqlSession1.getMapper(JdGoodsMapper.class);   |
        |final JdGoodsMapper mapper2 = sqlSession2.getMapper(JdGoodsMapper.class);   | 一级缓存每个会话会保存各自的缓存不会共享
        |         mapper1.selectByPrimaryKey(1);                                     |
        |  mapper2.selectByPrimaryKey(1);                                            |
        |----------------------------------------------------------------------------|*/
        final JdGoodsMapper mapper1 = sqlSession1.getMapper(JdGoodsMapper.class);
        final JdGoodsMapper mapper2 = sqlSession2.getMapper(JdGoodsMapper.class);
        mapper1.selectByPrimaryKey(1);
        final JdGoods jdGoods1 = mapper2.selectByPrimaryKey(1);
        System.out.println("-->"+jdGoods1.getTitle());
    }

    /**
     * 测试查询第二个缓存
     *
     * @return
     * @author jianlei.shi
     * @date 2021-03-12 14:52:21
     */
    @Test
    public void testQuerySecondCache() {
        SqlSessionFactory builder = new SqlSessionFactoryBuilder().build(JdGoodsMapperTest.class.getClassLoader().getResourceAsStream("mybatisTestConfiguration/JdGoodsMapperTestConfiguration.xml"));
        //you can use builder.openSession(false) to not commit to database
        SqlSession sqlSession1 = builder.openSession(true);
        SqlSession sqlSession2 = builder.openSession(true);
     /* |----------------------------------------------------------------------------|
        |final JdGoodsMapper mapper1 = sqlSession1.getMapper(JdGoodsMapper.class);   |
        |final JdGoodsMapper mapper2 = sqlSession2.getMapper(JdGoodsMapper.class);   | 一级缓存每个会话会保存各自的缓存不会共享
        |         mapper1.selectByPrimaryKey(1);                                     |
        |  mapper2.selectByPrimaryKey(1);                                            |
        |----------------------------------------------------------------------------|*/
        final JdGoodsMapper mapper1 = sqlSession1.getMapper(JdGoodsMapper.class);
        final JdGoodsMapper mapper2 = sqlSession2.getMapper(JdGoodsMapper.class);
        mapper1.selectByPrimaryKey(1);
        sqlSession1.commit(); //不提交不会走二级缓存
        /*JdGoods jdGoods=new JdGoods();
        jdGoods.setId(1);
        jdGoods.setTitle("Test 联想(Lenovo)小新Pro13.3英寸全面屏超轻薄笔记本电脑(标压锐龙R5-3550H 16G 512G 2.5K QHD 100%sRGB)黑色");
        jdGoods.setTitleLink("https://item.jd.com/100005171461.html");
        jdGoods.setThumbnail("https://img11.360buyimg.com/n7/jfs/t1/99733/2/8261/174001/5e045083Ec79b2c6e/fac2d957b511c1da.jpg");
        jdGoods.setRate("已有39万+人评价");
        jdGoods.setShopName("联想京东自营旗舰店");
        jdGoods.setOperate("加入购物车");
        mapper1.updateByPrimaryKey(jdGoods);*/
        final JdGoods jdGoods1 = mapper2.selectByPrimaryKey(1);
        System.out.println("-->"+jdGoods1.getTitle());
    }
}
