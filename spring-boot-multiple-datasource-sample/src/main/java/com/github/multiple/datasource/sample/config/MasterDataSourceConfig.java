package com.github.multiple.datasource.sample.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.common.constants.Constants;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;


/**
 * @author jianlei.shi
 * @date 2021/3/1 11:29 上午
 * @description MasterDataSourceConfig
 */
@Configuration
@MapperScan(basePackages = "com.github.multiple.datasource.sample.dao.master",sqlSessionFactoryRef = "masterSqlSessionFactory")
//@DependsOn("utils.SpringUtils")
public class MasterDataSourceConfig {
    @Value("${spring.datasource.master.url}")
    private String url;

    @Value("${spring.datasource.master.username}")
    private String user;

    @Value("${spring.datasource.master.password}")
    private String password;

    @Value("${spring.datasource.master.driverClassName}")
    private String driverClass;

    @Value("${spring.datasource.master.mapperLocations}")
    private String mapperLocations;


    @Bean("masterDataSource")
    @Primary
    public DataSource masterDataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }


    @Bean("masterSqlSessionFactory")
    @Primary
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("masterDataSource") DataSource masterDataSource) throws Exception {
//        final DataSource dataSource = SpringUtils.getBeanByName("masterDataSource");
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(masterDataSource);

        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(Constants.CLASS_PATH_PREFIX + (mapperLocations.startsWith(Constants.SPLIT_FACADE) ? mapperLocations : Constants.SPLIT_FACADE + mapperLocations)));
        final SqlSessionFactory sqlSessionFactory = sessionFactoryBean.getObject(); //顺序不能颠倒
        final org.apache.ibatis.session.Configuration configuration = sqlSessionFactory.getConfiguration();
            configuration.setMapUnderscoreToCamelCase(true);//开启驼峰
        return sessionFactoryBean.getObject();
    }

    @Bean(name = "masterTransactionManager")
    @Primary
    public DataSourceTransactionManager masterTransactionManager(@Qualifier("masterDataSource") DataSource masterDataSource) {
//        final DataSource dataSource = SpringUtils.getBeanByName("masterDataSource");
        return new DataSourceTransactionManager(masterDataSource);
    }
/**
<code>
 public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
 if (!this.externalSqlSession) {
 this.sqlSession = new SqlSessionTemplate(sqlSessionFactory); //自动创建不需要在创建,在mapper接口bean即MapperFactoryBean 创建将 masterSqlSessionFactory bean传入
 }
 }

 public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
 this.sqlSession = sqlSessionTemplate;
 this.externalSqlSession = true;
 }
 </code>


    @Bean(name = "masterSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate masterSqlSessionTemplate(@Qualifier("masterSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
*/


}
