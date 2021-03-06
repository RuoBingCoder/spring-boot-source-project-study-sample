package com.github.spring.components.learning.transaction.support;

import com.github.spring.components.learning.utils.SpringUtil;
import org.apache.ibatis.executor.result.DefaultResultHandler;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;

/**
 * @author: jianlei.shi
 * @date: 2020/12/22 5:09 下午
 * @description: DaoSupport
 */
//@Component
@Deprecated
public class DaoSupport implements InitializingBean {

    private SqlSessionTemplate sqlSession;
    private Configuration configuration;

    public List<Object> query(Class<?> sourceClass, String statement, Integer args) {
        List<Object> res = null;
//        Object obj = sourceClass.newInstance();
        ResultHandler<Object> resultHandler = new DefaultResultHandler();
        try {

            res = sqlSession.selectList(statement,args);
        } catch (Exception e) {
            throw new RuntimeException("query exception!", e);
        }
        return res;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        SqlSessionTemplate sqlSession = SpringUtil.getBeanByType(SqlSession.class);
        this.sqlSession = sqlSession;
        this.configuration = sqlSession.getConfiguration();
    }
}
