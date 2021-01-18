package com.github.spring.components.mybatis.registry;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ClassUtil;
import com.github.spring.components.SpringComponentsApplication;
import com.github.spring.components.exception.MapperException;
import com.github.spring.components.interceptor.MapperInterceptor;
import com.github.spring.components.mybatis.common.constant.SqlMethodEnums;
import com.github.spring.components.mybatis.common.constant.SqlTagConstant;
import com.github.spring.components.mybatis.common.mapper.SimpleBaseMapper;
import com.github.spring.components.mybatis.common.wrapper.MapperMethodWrapper;
import com.github.spring.components.transaction.pojo.HeroDo;
import com.github.spring.components.utils.AnnotationUtils;
import com.github.spring.components.utils.ClassUtils;
import com.github.spring.components.utils.SpringUtil;
import com.github.spring.components.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.executor.keygen.Jdbc3KeyGenerator;
import org.apache.ibatis.executor.keygen.NoKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.mapping.StatementType;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import utils.ReflectUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author jianlei.shi
 * @date: 2020/12/26 1:15 下午
 * @description: MapperRegistryUtils
 *
 * <p>
 *
 *
 * </P>
 */
@Component
@Slf4j
public class SimpleMapperRegistry implements InitializingBean, EnvironmentAware {

    private ConfigurableEnvironment environment;


    /**
     * ParameterMapping{property='id', mode=IN, javaType=class java.lang.Integer, jdbcType=INTEGER, numericScale=null, resultMapId='null', jdbcTypeName='null', expression='null'}
     */


    /**
     * @see com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder
     * @see
     */
    @Override
    public void afterPropertiesSet() {
        //TODO 后续改造成注解获取table 信息
        System.out.println("  _____ _                 _        __  __       _     _   _       _____  _           \n" +
                " / ____(_)               | |      |  \\/  |     | |   | | (_)     |  __ \\| |          \n" +
                "| (___  _ _ __ ___  _ __ | | ___  | \\  / |_   _| |__ | |_ _ ___  | |__) | |_   _ ___ \n" +
                " \\___ \\| | '_ ` _ \\| '_ \\| |/ _ \\ | |\\/| | | | | '_ \\| __| / __| |  ___/| | | | / __|\n" +
                " ____) | | | | | | | |_) | |  __/ | |  | | |_| | |_) | |_| \\__ \\ | |    | | |_| \\__ \\\n" +
                "|_____/|_|_| |_| |_| .__/|_|\\___| |_|  |_|\\__, |_.__/ \\__|_|___/ |_|    |_|\\__,_|___/\n" +
                "                   | |                     __/ |                                     \n" +
                "                   |_|                    |___/                                      \n");
        doAddCommonMappedStatement();
        /*String resource = HeroMapper.class.getName().replace('.', '/') + ".java (best guess)";
        MapperBuilderAssistant assistant = new MapperBuilderAssistant(configuration, resource);
        assistant.setCurrentNamespace("com.sjl.spring.components.transaction.dao.HeroMapper");*/
        //####################################### MybatisPlus ###################################################

//        DefaultSqlInjector sqlInjector = (DefaultSqlInjector) GlobalConfigUtils.getSqlInjector(configuration);
        /*SelectById selectById = new SelectById();
        assistant.setCurrentNamespace("com.sjl.spring.components.transaction.dao.HeroMapper");
        TableInfo tableInfo = TableInfoHelper.initTableInfo(assistant, Hero.class);
        selectById.inject(assistant, HeroMapper.class, Hero.class, tableInfo);*/

        //####################################### Mybatis ###################################################
        //扫描包


    }

    /**
     * @author jianlei.shi
     * @description 添加 sqlSource 到MappedStatement
     * @date 5:51 下午 2020/12/28
     **/
    private void doAddCommonMappedStatement() {
        SqlSessionTemplate sqlSession;
        Configuration configuration;
        try {
            sqlSession = SpringUtil.getBeanByType(SqlSession.class);
            configuration = sqlSession.getConfiguration();
            configuration.addInterceptor(new MapperInterceptor());
        } catch (Exception e) {
            log.error("获取SqlSession 异常!", e);
            throw new MapperException("获取SqlSession 异常");

        }

        String daoScan = ClassUtils.getMapperScanAnnotationValue(SpringComponentsApplication.class, MapperScan.class);
        Assert.notNull(daoScan, "dao 包路径不能为空!");
        Set<Class<?>> scanPackage = ClassUtil.scanPackage(daoScan);
        Map<Class<?>, List<MapperMethodWrapper>> handleSqlMethod = handleSqlMethod(scanPackage);
        if (CollectionUtil.isNotEmpty(handleSqlMethod)) {
            for (Map.Entry<Class<?>, List<MapperMethodWrapper>> entry : handleSqlMethod.entrySet()) {
                LanguageDriver languageDriver = configuration.getDefaultScriptingLanguageInstance();
                String source = entry.getKey().getName().replace('.', '/') + ".java (best guess)";
                MapperBuilderAssistant builderAssistant = new MapperBuilderAssistant(configuration, source);
                builderAssistant.setCurrentNamespace(entry.getKey().getName());
                try {
                    for (MapperMethodWrapper methodWrapper : entry.getValue()) {
                        if (methodWrapper.getSql().equals(SqlMethodEnums.SELECT_LIST.getSql())) {
                            SqlSource sqlSource = languageDriver.createSqlSource(configuration, String.format(methodWrapper.getSql(), baseColumnList(methodWrapper.getEntityType()), AnnotationUtils.getTableName(methodWrapper.getEntityType()), whereIfWrapper(methodWrapper.getEntityType())), methodWrapper.getEntityType());
                            addMappedStatement(builderAssistant, methodWrapper.getID(), sqlSource, SqlCommandType.SELECT, methodWrapper.getReturnType(), NoKeyGenerator.INSTANCE, languageDriver, null, null);
                        } else if (methodWrapper.getSql().equals(SqlMethodEnums.INSERT_ONE.getSql())) {
                            SqlSource sqlSource = languageDriver.createSqlSource(configuration, String.format(methodWrapper.getSql(), AnnotationUtils.getTableName(methodWrapper.getEntityType()), insertColumnWrapper(methodWrapper.getEntityType()), insertValuesWrapper(methodWrapper.getEntityType())), methodWrapper.getEntityType());
                            addMappedStatement(builderAssistant, methodWrapper.getID(), sqlSource, SqlCommandType.INSERT, methodWrapper.getReturnType(), Jdbc3KeyGenerator.INSTANCE, languageDriver, SqlTagConstant.KEY_COLUMN, SqlTagConstant.KEY_PROPERTY);
                        } else if (methodWrapper.getSql().equals(SqlMethodEnums.DELETE.getSql())) {
                            SqlSource sqlSource = languageDriver.createSqlSource(configuration, String.format(methodWrapper.getSql(), AnnotationUtils.getTableName(methodWrapper.getEntityType()), whereIfWrapper(methodWrapper.getEntityType())), methodWrapper.getEntityType());
                            addMappedStatement(builderAssistant, methodWrapper.getID(), sqlSource, SqlCommandType.DELETE, methodWrapper.getReturnType(), NoKeyGenerator.INSTANCE, languageDriver, SqlTagConstant.KEY_COLUMN, SqlTagConstant.KEY_PROPERTY);
                        } else if (methodWrapper.getSql().equals(SqlMethodEnums.UPDATE.getSql())) {
                            SqlSource sqlSource = languageDriver.createSqlSource(configuration, String.format(methodWrapper.getSql(),AnnotationUtils.getTableName(methodWrapper.getEntityType()), updateSetColumn(methodWrapper.getEntityType()), whereIfWrapper(methodWrapper.getEntityType())), null);
                            addMappedStatement(builderAssistant, methodWrapper.getID(), sqlSource, SqlCommandType.UPDATE, methodWrapper.getReturnType(), NoKeyGenerator.INSTANCE, languageDriver, SqlTagConstant.KEY_COLUMN, SqlTagConstant.KEY_PROPERTY);

                        }
                    }
                } catch (Exception e) {
                    log.error("添加addMappedStatement 异常!",e);
                    throw new MapperException("添加addMappedStatement 异常");

                }
            }
        }
    }

    /**
     * @param entityType
     * @return sql 列
     * @author jianlei.shi
     * @description 拼接sql 列
     * @date 11:58 上午 2020/12/30
     **/
    private String baseColumnList(Class<?> entityType) {
        StringBuilder sb = new StringBuilder();
        for (Field field : entityType.getDeclaredFields()) {
            String column = StringUtils.lowerUnderscore(field.getName());
            sb.append(column).append(SqlTagConstant.SEGMENTATION);
        }
        return StringUtils.formatPlaceholder(sb.toString());
    }

    /**
     * @author jianlei.shi
     * @description 添加sqlSource到MappedStatement
     * @date 4:40 下午 2020/12/27
     * @see Configuration#addMappedStatement(MappedStatement)
     **/
    private void addMappedStatement(MapperBuilderAssistant builderAssistant, String id, SqlSource sqlSource, SqlCommandType select, Class<?> returnType, Object instance, LanguageDriver languageDriver, String keyColumn, String keyProp) {
        builderAssistant.addMappedStatement(
                id,
                sqlSource,
                StatementType.PREPARED,
                select,
                null,
                null,
                null,
                null,
                null,
                returnType,
                null,
                false,
                true,
                false,
                instance instanceof NoKeyGenerator ? NoKeyGenerator.INSTANCE : Jdbc3KeyGenerator.INSTANCE,
                keyProp,
                keyColumn,
                null,
                languageDriver);

    }

    /**
     * @param paramType 添加的entity
     * @return update <set></set>
     * @author jianlei.shi
     * @description 设置update set脚本
     * @date 4:41 下午 2020/12/27
     **/
    private static String updateSetColumn(Class<?> paramType) {
        Field[] fields = getDecFields(paramType);
        StringBuilder sb = new StringBuilder();
        sb.append(SqlTagConstant.SET_START_TAG);
        commonSetColumnBuild(fields, sb);
        String setScript = StringUtils.formatPlaceholder(sb.toString());
        setScript += SqlTagConstant.SET_END_TAG;
        return setScript;
    }

    /**
     * @param fields 字段集合
     * @author jianlei.shi
     * @description update set 公共脚本建立
     * @date 4:42 下午 2020/12/27
     **/
    private static void commonSetColumnBuild(Field[] fields, StringBuilder sb) {
        for (Field field : fields) {
            //TODO 待换成注解获取主健
            if (SqlTagConstant.PRIMARY_KEY.equals(field.getName())) {
                continue;
            }
            commonSetFormat(sb, field);
            sb.append(SqlTagConstant.IF_END_TAG);
        }
    }

    /**
     * @param field 字段
     * @author jianlei.shi
     * @description update set 格式化
     * @date 4:42 下午 2020/12/27
     **/
    private static void commonSetFormat(StringBuilder sb, Field field) {
        String format = String.format(SqlTagConstant.IF_NON_AND_START_TAG, SqlTagConstant.PARAMS_PREFIX + field.getName(), SqlTagConstant.PARAMS_PREFIX + field.getName());
        sb.append(format);
        sb.append(StringUtils.lowerUnderscore(field.getName()));
        sb.append(SqlTagConstant.LEFT_EQ_PLACEHOLDER);
        sb.append(SqlTagConstant.PARAMS_PREFIX);
        sb.append(field.getName());
        sb.append(SqlTagConstant.RIGHT_PLACEHOLDER);
        sb.append(SqlTagConstant.SEGMENTATION);
    }

    private static Field[] getDecFields(Class<?> paramType) {
        return paramType.getDeclaredFields();
    }

    /**
     * @param sb    脚本字符串
     * @param field 字段
     * @return null
     * @author jianlei.shi
     * @description 公共的sql格式化
     * @date 4:43 下午 2020/12/27
     **/
    private static void commonSqlFormat(StringBuilder sb, Field field) {
        String format = String.format(SqlTagConstant.IF_START_TAG, SqlTagConstant.QUERY_PARAMS_PREFIX + field.getName(), SqlTagConstant.QUERY_PARAMS_PREFIX + field.getName());
        sb.append(format);
        sb.append(StringUtils.lowerUnderscore(field.getName()));
        sb.append(SqlTagConstant.LEFT_EQ_PLACEHOLDER);
        sb.append(SqlTagConstant.QUERY_PARAMS_PREFIX).append(field.getName());
        sb.append(SqlTagConstant.RIGHT_PLACEHOLDER);
        sb.append(SqlTagConstant.IF_END_TAG);
    }

    /**
     * @param paramType 添加的entity
     * @return insert <set></set> 脚本
     * @author jianlei.shi
     * @description insert into XXX (..,..) 脚本构造
     * @date 4:45 下午 2020/12/27
     **/
    private static String insertColumnWrapper(Class<?> paramType) {
        Field[] fields = paramType.getDeclaredFields();
        StringBuilder sb = new StringBuilder();
        sb.append(SqlTagConstant.LEFT_BRACKETS);
        for (Field field : fields) {
            if (SqlTagConstant.PRIMARY_KEY.equals(field.getName())) {
                continue;
            }
            sb.append(StringUtils.lowerUnderscore(field.getName()));
            sb.append(SqlTagConstant.SEGMENTATION);

        }
        sb.append(SqlTagConstant.RIGHT_BRACKETS);
        return StringUtils.formatPlaceholder(sb.toString());
    }

    /**
     * @param paramType 添加的entity
     * @return values script
     * @author jianlei.shi
     * @description insert values 脚本构造
     * @date 4:45 下午 2020/12/27
     **/
    private static String insertValuesWrapper(Class<?> paramType) {
        StringBuilder resSql = new StringBuilder();
        resSql.append(SqlTagConstant.LEFT_BRACKETS);
        Field[] fields = paramType.getDeclaredFields();
        for (Field field : fields) {
            if (SqlTagConstant.PRIMARY_KEY.equals(field.getName())) {
                continue;
            }
            resSql.append(SqlTagConstant.LEFT_PLACEHOLDER);
            resSql.append(field.getName());
            resSql.append(SqlTagConstant.RIGHT_PLACEHOLDER);
            resSql.append(SqlTagConstant.SEGMENTATION);
        }
        resSql.append(SqlTagConstant.RIGHT_BRACKETS);
        return StringUtils.formatPlaceholder(resSql.toString());

    }

    /**
     * @param paramType 添加的entity
     * @return where 条件 script
     * @author jianlei.shi
     * @description 动态sql 条件脚本构造
     * @date 4:46 下午 2020/12/27
     **/
    private static String whereIfWrapper(Class<?> paramType) {
        StringBuilder whereScript = new StringBuilder();
        whereScript.append(SqlTagConstant.WHERE_TAG_START);
        Field[] fields = getDecFields(paramType);
        for (Field field : fields) {
            commonSqlFormat(whereScript, field);
        }
        whereScript.append(SqlTagConstant.WHERE_END_TAG);
        if (log.isDebugEnabled()){
            log.debug("===>>where sql:{}", whereScript.toString());
        }
        return whereScript.toString();
    }

    /**
     * @param scanPackage 接口集合
     * @return mapper方法集合
     * @author jianlei.shi
     * @description 处理接口 合并公共mapper 方法
     * @date 4:46 下午 2020/12/27
     **/
    private Map<Class<?>, List<MapperMethodWrapper>> handleSqlMethod(Set<Class<?>> scanPackage) {
        Map<Class<?>, List<MapperMethodWrapper>> commonMethodMap = new LinkedHashMap<>();
        if (CollectionUtil.isNotEmpty(scanPackage)) {
            List<MapperMethodWrapper> methodWrappers = null;
            for (Class<?> inf : scanPackage) {
                if (SimpleBaseMapper.class.isAssignableFrom(inf)) {
                    methodWrappers = new ArrayList<>();
                    final Object typeArguments = ReflectUtils.getGenericSingleType(inf);
                    //insert
                        MapperMethodWrapper selMethodWrapper = MapperMethodWrapper.builder().ID(SqlMethodEnums.INSERT_ONE.getMethod()).entityType((Class<?>) typeArguments)
                                .sql(SqlMethodEnums.INSERT_ONE.getSql())
                                .returnType(int.class).build();
                        methodWrappers.add(selMethodWrapper);
                        //selectList
                        MapperMethodWrapper selListMethodWrapper = MapperMethodWrapper.builder().ID(SqlMethodEnums.SELECT_LIST.getMethod()).entityType((Class<?>) typeArguments)
                                .sql(SqlMethodEnums.SELECT_LIST.getSql())
                                .returnType((Class<?>) typeArguments).build();
                        methodWrappers.add(selListMethodWrapper);
                        //updateBySelective
                        MapperMethodWrapper updateBySelectiveMethodWrapper = MapperMethodWrapper.builder().ID(SqlMethodEnums.UPDATE.getMethod()).entityType((Class<?>) typeArguments)
                                .sql(SqlMethodEnums.UPDATE.getSql())
                                .returnType(int.class).build();
                        methodWrappers.add(updateBySelectiveMethodWrapper);
                        //delete
                        MapperMethodWrapper deleteBySelectiveMethodWrapper = MapperMethodWrapper.builder().ID(SqlMethodEnums.DELETE.getMethod()).entityType((Class<?>) typeArguments)
                                .sql(SqlMethodEnums.DELETE.getSql())
                                .returnType(int.class).build();
                        methodWrappers.add(deleteBySelectiveMethodWrapper);
                    }
                    commonMethodMap.put(inf, methodWrappers);
                }
            }

        return commonMethodMap;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = (ConfigurableEnvironment) environment;
    }

    public static void main(String[] args) {
//        System.out.println(whereIfWrapper(Hero.class));

//        System.out.println(insertColumnWrapper(Hero.class));
//
//        System.out.println(updateSetColumn(Hero.class));

//        System.out.println(updateWhereIfWrapper(Hero.class));
//        System.out.println(HeroMapper.class.getName());
        System.out.println(insertValuesWrapper(HeroDo.class));
//        System.out.println(baseColumnList(Hero.class));

    }

}
