package com.github.spring.components.learning.transaction.dao;


import com.github.spring.components.learning.enhancemybatis.common.mapper.SimpleBaseMapper;
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
 * 在上文中提到的一级缓存中，其最大的共享范围就是一个SqlSession内部，如果多个SqlSession之间需要共享缓存，则需要使用到二级缓存。开启二级缓存后，会使用CachingExecutor装饰Executor，
 * 进入一级缓存的查询流程前，先在CachingExecutor进行二级缓存的查询，
 * <code>
 *     public <E> List<E> query(MappedStatement ms, Object parameterObject, RowBounds rowBounds, ResultHandler resultHandler, CacheKey key, BoundSql boundSql) throws SQLException {
 *         Cache cache = ms.getCache();
 *         if (cache != null) {
 *             this.flushCacheIfRequired(ms);
 *             if (ms.isUseCache() && resultHandler == null) {
 *                 this.ensureNoOutParams(ms, boundSql);
 *
 *                 List<E> list = (List)this.tcm.getObject(cache, key); //二级缓存中获取没有命中则委托调用BaseExecutor.query()查询数据
 *                 if (list == null) {
 *                     list = this.delegate.query(ms, parameterObject, rowBounds, resultHandler, key, boundSql);
 *                     this.tcm.putObject(cache, key, list);
 *                 }
 *
 *                 return list;
 *             }
 *         }
 *
 *         return this.delegate.query(ms, parameterObject, rowBounds, resultHandler, key, boundSql);
 *     }
 * </code>
 * 一二级缓存
 * @see JdGoodsMapperTest
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