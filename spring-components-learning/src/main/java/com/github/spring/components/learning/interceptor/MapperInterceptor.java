package com.github.spring.components.learning.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.github.mybatis.constant.MapperConstant;
import com.github.spring.components.learning.exception.MapperException;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.Connection;
import java.util.List;
import java.util.Properties;

/**
 * @author: jianlei.shi
 * @date: 2020/12/28 7:26 下午
 * @description: MapperInterceptor
 * @see org.apache.ibatis.plugin.Plugin#getSignatureMap(Interceptor)  获取注解信息
 * @see org.apache.ibatis.session.Configuration#newStatementHandler(Executor, MappedStatement, Object, RowBounds, ResultHandler, BoundSql)  将{@link Interceptor} 遍历做代理执行{@link Interceptor#plugin(Object)}
 */
/*@Intercepts({
        @Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class }),
        @Signature(type = Executor.class, method = "close", args = { boolean.class }) })*/
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
@Slf4j
public class MapperInterceptor implements Interceptor {

    /**
     * @param invocation
     * @return
     * @author jianlei.shi
     * @description 拦截器 和 plugin 是在sql准备执行期间先做代理再进行invoke
     * @date 10:28 上午 2020/12/31
     **/
    @Override
    public Object intercept(Invocation invocation) throws Throwable {

//        ResultSetHandler resultSetHandler = (ResultSetHandler) invocation.getTarget();

        // 获取MappedStatement,Configuration对象
       /* MetaObject metaObject =
                MetaObject.forObject(resultSetHandler, new DefaultObjectFactory(), new DefaultObjectWrapperFactory(), new DefaultReflectorFactory());
        MappedStatement mappedStatement = (MappedStatement) metaObject.getValue("mappedStatement");
        String statement = mappedStatement.getId();
        if (!isPageSql(statement,metaObject.getValue("boundSql.parameterObject"))) {
            return invocation.proceed();
        }

        // 获取分页参数
        QPageQuery pageQuery = (QPageQuery) metaObject.getValue("boundSql.parameterObject");

        List<PageResult> result = new ArrayList<PageResult>(1);
        PageResult page = new PageResult();
        page.setPagination(pageQuery.getPagination());
        page.setResult((List) invocation.proceed());
        result.add(page);*/
        long startTime = System.currentTimeMillis();
        try {

             // {@code Signature(type = StatementHandler.class)} -> invocation.getTarget(); 类型要保持一致

            StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
            MetaObject metaObject =
                    MetaObject.forObject(statementHandler, new DefaultObjectFactory(), new DefaultObjectWrapperFactory(), new DefaultReflectorFactory());
            MappedStatement mappedStatement = (MappedStatement) metaObject.getValue(MapperConstant.DELEGATE_MAPPED_STATEMENT);
            log.info("-->> Statement ID :{}", mappedStatement.getId());
            BoundSql boundSql = statementHandler.getBoundSql();
            String sql = boundSql.getSql();
            Object parameterObject = boundSql.getParameterObject();
            List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
            log.info("===>parameterMappings is:{}",JSONObject.toJSONString(parameterMappings));
            log.info("==>>Interceptor query sql :{} parameterObject is:{}", sql, JSONObject.toJSONString(parameterObject));
            return invocation.proceed();
        } catch (Exception e) {
            log.error("MapperInterceptor intercept exception", e);
            throw new MapperException("MapperInterceptor intercept exception");
        } finally {
            long endTime = System.currentTimeMillis();
            log.info("-------->sql executor time :{}", (endTime - startTime)+"ms");

        }
    }


    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }


    @Override
    public void setProperties(Properties properties) {
    }
}
