package com.sjl.spring.components.transaction.dao;

import com.sjl.spring.components.transaction.pojo.Hero;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.session.Configuration;


/**
 * <pre>
 *     扫描包定义BeanDefinition {@link org.mybatis.spring.mapper.ClassPathMapperScanner#processBeanDefinitions} 将构造器参数注入为{@code mapperInterface}  BeanClass 替换为{@link org.mybatis.spring.mapper.MapperFactoryBean}
 * </pre>
 *
 * @see org.mybatis.spring.mapper.MapperFactoryBean#checkDaoConfig()
 * @see org.mybatis.spring.mapper.MapperFactoryBean#getObject()
 * @see org.apache.ibatis.session.defaults.DefaultSqlSession#getMapper(Class)
 * @see org.apache.ibatis.binding.MapperProxyFactory#newInstance(SqlSession)
 * <p>
 * 1、校验dao接口,添加到Map中
 * 2、依赖注入的时候调用{@code getObject()}从map中获取接口创建代理
 * </P>
 * @see MapperMethod.SqlCommand#resolveMappedStatement(Class, String, Class, Configuration)  解析stament for example: ID: com.sjl.spring.components.transaction.dao.JdGoodsMapper.selectByPrimaryKey
 *
 * @see org.apache.ibatis.logging.jdbc.ConnectionLogger#debug =>Preparing: sql
 */
public interface HeroMapper {
    int insert(Hero record);

    int insertSelective(Hero record);
}