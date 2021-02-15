package com.github.spring.components.learning.transaction.dao;


import com.github.spring.components.learning.mybatis.common.mapper.SimpleBaseMapper;
import com.github.spring.components.learning.transaction.pojo.HeroDo;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionFactoryBean;

import javax.sql.DataSource;


/**
 * <pre>
 *     扫描包定义BeanDefinition {@link org.mybatis.spring.mapper.ClassPathMapperScanner#processBeanDefinitions} 将构造器参数注入为{@code mapperInterface}  BeanClass 替换为{@link org.mybatis.spring.mapper.MapperFactoryBean}
 * </pre>
 *
 * @see org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration#sqlSessionFactory(DataSource)
 * @see SqlSessionFactoryBean#afterPropertiesSet() //builder 开始解析xml
 * @see org.apache.ibatis.builder.MapperBuilderAssistant#addMappedStatement   添加sql信息
 * @see org.mybatis.spring.mapper.MapperFactoryBean#checkDaoConfig()
 * @see org.mybatis.spring.mapper.MapperFactoryBean#getObject()
 * @see org.apache.ibatis.session.defaults.DefaultSqlSession#getMapper(Class)
 * @see org.apache.ibatis.binding.MapperProxyFactory#newInstance(SqlSession)
 * <p>
 * 1、校验dao接口,添加到Map中
 * 2、依赖注入的时候调用{@code getObject()}从map中获取接口创建代理
 * </P>
 * @see MapperMethod.SqlCommand#resolveMappedStatement(Class, String, Class, Configuration)  解析stament for example: ID: com.sjl.spring.components.transaction.dao.JdGoodsMapper.selectByPrimaryKey
 * @see org.apache.ibatis.logging.jdbc.ConnectionLogger#debug =>Preparing: sql
 *
 * 创建一级缓存
 * @see org.apache.ibatis.executor.CachingExecutor#createCacheKey(MappedStatement, Object, RowBounds, BoundSql)
 */
public interface HeroMapper extends SimpleBaseMapper<HeroDo> {
//    int insert(Hero record);

    int insertSelective(HeroDo record);


//    Hero selectById(Integer id);
    /**
     * @author jianlei.shi
     * @description mybatis解析优先级 xml>注解
     * @date 11:04 上午 2020/12/28
     * @param null
     * @return
     * @see org.apache.ibatis.builder.annotation.MapperAnnotationBuilder#parse
     * @see org.apache.ibatis.builder.annotation.MapperAnnotationBuilder#getSqlSourceFromAnnotations
     * (String[]) sqlAnnotation.getClass().getMethod("value").invoke(sqlAnnotation);
     **/
//     @Update(value = "update `hero` set `name`=#{name},`type`=#{type},`money`=#{money},`create_time`=#{createTime} where id=#{id}")
//     int updateById(Integer id);


}