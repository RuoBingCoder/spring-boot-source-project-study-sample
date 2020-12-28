package com.sjl.spring.components.mybatis.registry;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ClassUtil;
import com.sjl.spring.components.mybatis.common.constant.SqlMethodEnums;
import com.sjl.spring.components.mybatis.common.constant.SqlTagConstant;
import com.sjl.spring.components.mybatis.common.mapper.SimpleBaseMapper;
import com.sjl.spring.components.mybatis.common.wrapper.MapperMethodWrapper;
import com.sjl.spring.components.transaction.pojo.Hero;
import com.sjl.spring.components.utils.SpringUtil;
import com.sjl.spring.components.utils.StringUtils;
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
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @author: jianlei.shi
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
     * @throws Exception
     * @see com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder
     * @see
     */
    @Override
    public void afterPropertiesSet() throws Exception {
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

    private void doAddCommonMappedStatement() {
        SqlSessionTemplate sqlSession = SpringUtil.getBeanByType(SqlSession.class);
        Configuration configuration = sqlSession.getConfiguration();
        String daoScan = environment.getProperty(SqlTagConstant.DAO_SCAN_PACKAGES_PREFIX);
        Assert.notNull(daoScan, "dao 包路径不能为空!");
        Set<Class<?>> scanPackage = ClassUtil.scanPackage(daoScan);
        Map<Class<?>, List<MapperMethodWrapper>> handleSqlMethod = handleSqlMethod(scanPackage);
        if (CollectionUtil.isNotEmpty(handleSqlMethod)) {
            for (Map.Entry<Class<?>, List<MapperMethodWrapper>> entry : handleSqlMethod.entrySet()) {
                LanguageDriver languageDriver = configuration.getDefaultScriptingLanguageInstance();
                String source = entry.getKey().getName().replace('.', '/') + ".java (best guess)";
                MapperBuilderAssistant builderAssistant = new MapperBuilderAssistant(configuration, source);
                builderAssistant.setCurrentNamespace(entry.getKey().getName());
                for (MapperMethodWrapper methodWrapper : entry.getValue()) {
                    if (methodWrapper.getSql().equals(SqlMethodEnums.SELECT_LIST.getSql())) {
                        SqlSource sqlSource = languageDriver.createSqlSource(configuration, String.format(methodWrapper.getSql(),SqlTagConstant.STAR_SYMBOL, StringUtils.lowerUnderscore(methodWrapper.getEntityType().getSimpleName()), whereIfWrapper(methodWrapper.getEntityType())), methodWrapper.getEntityType());
                        addMappedStatement(builderAssistant, methodWrapper.getID(), sqlSource, SqlCommandType.SELECT, methodWrapper.getReturnType(), NoKeyGenerator.INSTANCE, languageDriver, null, null);
                    } else if (methodWrapper.getSql().equals(SqlMethodEnums.INSERT_ONE.getSql())) {
                        SqlSource sqlSource = languageDriver.createSqlSource(configuration, String.format(methodWrapper.getSql(), StringUtils.lowerUnderscore(methodWrapper.getEntityType().getSimpleName()), setWrapper(methodWrapper.getEntityType()), valuesWrapper(methodWrapper.getEntityType())), methodWrapper.getEntityType());
                        addMappedStatement(builderAssistant, methodWrapper.getID(), sqlSource, SqlCommandType.INSERT, methodWrapper.getReturnType(), Jdbc3KeyGenerator.INSTANCE, languageDriver, SqlTagConstant.KEY_COLUMN, SqlTagConstant.KEY_PROPERTY);
                    } else if (methodWrapper.getSql().equals(SqlMethodEnums.DELETE.getSql())) {
                        SqlSource sqlSource = languageDriver.createSqlSource(configuration, String.format(methodWrapper.getSql(), StringUtils.lowerUnderscore(methodWrapper.getEntityType().getSimpleName()), whereIfWrapper(methodWrapper.getEntityType())), methodWrapper.getEntityType());
                        addMappedStatement(builderAssistant, methodWrapper.getID(), sqlSource, SqlCommandType.DELETE, methodWrapper.getReturnType(), NoKeyGenerator.INSTANCE, languageDriver, SqlTagConstant.KEY_COLUMN, SqlTagConstant.KEY_PROPERTY);
                    } else if (methodWrapper.getSql().equals(SqlMethodEnums.UPDATE.getSql())) {
                        SqlSource sqlSource = languageDriver.createSqlSource(configuration, String.format(methodWrapper.getSql(), StringUtils.lowerUnderscore(methodWrapper.getEntityType().getSimpleName()), updateSet(methodWrapper.getEntityType()), updateWhereIfWrapper(methodWrapper.getWhereType())), methodWrapper.getEntityType());
                        addMappedStatement(builderAssistant, methodWrapper.getID(), sqlSource, SqlCommandType.UPDATE, methodWrapper.getReturnType(), NoKeyGenerator.INSTANCE, languageDriver, SqlTagConstant.KEY_COLUMN, SqlTagConstant.KEY_PROPERTY);

                    }
                }
            }
        }
    }

    /**
     * @param
     * @return
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
     * @param null
     * @return
     * @author jianlei.shi
     * @description 设置update set脚本
     * @date 4:41 下午 2020/12/27
     **/
    private static String updateSet(Class<?> paramType) {
        Field[] fields = getDecFields(paramType);
        StringBuilder sb = new StringBuilder();
        sb.append(SqlTagConstant.SET_START_TAG);
        commonSetBuild(fields, sb);
        String setScript = StringUtils.split(sb.toString());
        setScript += SqlTagConstant.SET_END_TAG;
        return setScript;
    }

    /**
     * @param null
     * @return
     * @author jianlei.shi
     * @description update set 公共脚本建立
     * @date 4:42 下午 2020/12/27
     **/
    private static void commonSetBuild(Field[] fields, StringBuilder sb) {
        for (Field field : fields) {
            commonSetFormat(sb, field);
            sb.append(SqlTagConstant.IF_TAG_E);
        }
    }

    /**
     * @param null
     * @return
     * @author jianlei.shi
     * @description update set 格式化
     * @date 4:42 下午 2020/12/27
     **/
    private static void commonSetFormat(StringBuilder sb, Field field) {
        String format = String.format(SqlTagConstant.IF_TAG_NA_S, SqlTagConstant.ET+SqlTagConstant.POINT+field.getName(),  SqlTagConstant.ET+SqlTagConstant.POINT+field.getName());
        sb.append(format);
        sb.append(StringUtils.lowerUnderscore(field.getName()));
        sb.append(SqlTagConstant.LEFT_EQ_PLACE_HOLDER);
        sb.append(SqlTagConstant.ET+SqlTagConstant.POINT);
        sb.append(field.getName());
        sb.append(SqlTagConstant.RIGHT_PLACE_HOLDER);
        sb.append(SqlTagConstant.SEGMENTATION);
    }

    /**
     * @param null
     * @return
     * @author jianlei.shi
     * @description update 条件构造
     * @date 4:43 下午 2020/12/27
     **/
    private static String updateWhereIfWrapper(Class<?> paramType) {
        Field[] fields = getDecFields(paramType);
        StringBuilder sbw = new StringBuilder();
        sbw.append(SqlTagConstant.WHERE_START);
        for (Field field : fields) {
//            sbw.append(field.getName()).append(SqlTagConstant.LEFT_EQ_PLACE_HOLDER).append(field.getName()).append(SqlTagConstant.RIGHT_PLACE_HOLDER);
            updateScriptWhereFormat(sbw,field);
        }
        sbw.append(SqlTagConstant.WHERE_END);
        return sbw.toString();

    }

    private static void updateScriptWhereFormat(StringBuilder sb, Field field) {
            String format = String.format(SqlTagConstant.IF_TAG_S, SqlTagConstant.QUERY+SqlTagConstant.POINT+field.getName(), SqlTagConstant.QUERY+SqlTagConstant.POINT+field.getName());
            sb.append(format);
            sb.append(StringUtils.lowerUnderscore(field.getName()));
            sb.append(SqlTagConstant.LEFT_EQ_PLACE_HOLDER);
            sb.append(SqlTagConstant.QUERY + SqlTagConstant.POINT).append(field.getName());
            sb.append(SqlTagConstant.RIGHT_PLACE_HOLDER);
            sb.append(SqlTagConstant.IF_TAG_E);
    }

    private static Field[] getDecFields(Class<?> paramType) {
        return paramType.getDeclaredFields();
    }

    /**
     * @param null
     * @return
     * @author jianlei.shi
     * @description 公共的sql格式化
     * @date 4:43 下午 2020/12/27
     **/
    private static void commonSqlFormat(StringBuilder sb, Field field, String ifEnd) {
        String format = String.format(SqlTagConstant.IF_TAG_S, field.getName(), field.getName());
        sb.append(format);
        sb.append(StringUtils.lowerUnderscore(field.getName()));
        sb.append(SqlTagConstant.LEFT_EQ_PLACE_HOLDER);
        sb.append(field.getName());
        sb.append(SqlTagConstant.RIGHT_PLACE_HOLDER);
        sb.append(ifEnd);
    }

    /**
     * @param null
     * @return
     * @author jianlei.shi
     * @description insert into XXX (..,..) 脚本构造
     * @date 4:45 下午 2020/12/27
     **/
    private static String setWrapper(Class<?> paramType) {
        Field[] fields = paramType.getDeclaredFields();
        StringBuilder sb = new StringBuilder();
        sb.append(SqlTagConstant.LEFT_PARENTHESIS);
        int i = 0;
        for (Field field : fields) {
            i++;
            if ("id".equals(field.getName())) {
                continue;
            }
            sb.append(StringUtils.lowerUnderscore(field.getName()));

            if (i <= fields.length - 1) {
                sb.append(SqlTagConstant.SEGMENTATION);
            }
        }
        sb.append(SqlTagConstant.RIGHT_PARENTHESIS);
        return sb.toString();
    }

    /**
     * @param null
     * @return
     * @author jianlei.shi
     * @description insert values 脚本构造
     * @date 4:45 下午 2020/12/27
     **/
    private static String valuesWrapper(Class<?> paramType) {
        StringBuilder resSql = new StringBuilder();
        resSql.append(SqlTagConstant.LEFT_PARENTHESIS);
        Field[] fields = paramType.getDeclaredFields();
        int i = 0;
        for (Field field : fields) {
            i++;
            if ("id".equals(field.getName())) {
                continue;
            }
            resSql.append(SqlTagConstant.LEFT_PLACE_HOLDER);
            resSql.append(field.getName());
            resSql.append(SqlTagConstant.RIGHT_PLACE_HOLDER);

            if (i <= fields.length - 1) {
                resSql.append(SqlTagConstant.SEGMENTATION);
            }
        }
        resSql.append(SqlTagConstant.RIGHT_PARENTHESIS);
        return resSql.toString();

    }

    /**
     * @param null
     * @return
     * @author jianlei.shi
     * @description 动态sql 条件脚本构造
     * @date 4:46 下午 2020/12/27
     **/
    private static String whereIfWrapper(Class<?> paramType) {
        StringBuilder resSql = new StringBuilder();
        resSql.append(SqlTagConstant.WHERE_START);
        Field[] fields = paramType.getDeclaredFields();
        for (Field field : fields) {
            commonSqlFormat(resSql, field, SqlTagConstant.IF_TAG_E);
        }
        resSql.append(SqlTagConstant.WHERE_END);
        log.info("===>>where sql:{}", resSql.toString());
        return resSql.toString();
    }

    /**
     * @param null
     * @return
     * @author jianlei.shi
     * @description 处理接口 合并公共mapper 方法
     * @date 4:46 下午 2020/12/27
     **/
    private Map<Class<?>, List<MapperMethodWrapper>> handleSqlMethod(Set<Class<?>> scanPackage) {
        Map<Class<?>, List<MapperMethodWrapper>> commonMethodMap = new LinkedHashMap<>();
        if (CollectionUtil.isNotEmpty(scanPackage)) {
            List<MapperMethodWrapper> methodWrappers;
            for (Class<?> inf : scanPackage) {
                if (SimpleBaseMapper.class.isAssignableFrom(inf)) {
                    methodWrappers = new ArrayList<>();
                    Type[] genericInterfaces = inf.getGenericInterfaces();
                    Type type = genericInterfaces[0];
                    if (type instanceof ParameterizedType) {
                        ParameterizedType pt = (ParameterizedType) type;
                        Type[] typeArguments = pt.getActualTypeArguments();
                        //insert
                        MapperMethodWrapper selMethodWrapper = MapperMethodWrapper.builder().ID(SqlMethodEnums.INSERT_ONE.getMethod()).entityType((Class<?>) typeArguments[0])
                                .sql(SqlMethodEnums.INSERT_ONE.getSql())
                                .returnType(int.class).build();
                        methodWrappers.add(selMethodWrapper);
                        //selectList
                        MapperMethodWrapper selListMethodWrapper = MapperMethodWrapper.builder().ID(SqlMethodEnums.SELECT_LIST.getMethod()).entityType((Class<?>) typeArguments[0])
                                .sql(SqlMethodEnums.SELECT_LIST.getSql())
                                .returnType((Class<?>) typeArguments[0]).build();
                        methodWrappers.add(selListMethodWrapper);
                        //updateBySelective
                        MapperMethodWrapper updateBySelectiveMethodWrapper = MapperMethodWrapper.builder().ID(SqlMethodEnums.UPDATE.getMethod()).entityType((Class<?>) typeArguments[0])
                                .sql(SqlMethodEnums.UPDATE.getSql())
                                .whereType((Class<?>) typeArguments[1])
                                .returnType(int.class).build();
                        methodWrappers.add(updateBySelectiveMethodWrapper);
                        //delete
                        MapperMethodWrapper deleteBySelectiveMethodWrapper = MapperMethodWrapper.builder().ID(SqlMethodEnums.DELETE.getMethod()).entityType((Class<?>) typeArguments[0])
                                .sql(SqlMethodEnums.DELETE.getSql())
                                .returnType(int.class).build();
                        methodWrappers.add(deleteBySelectiveMethodWrapper);
                    }
                    commonMethodMap.put(inf, methodWrappers);
                }
            }

        }
        return commonMethodMap;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = (ConfigurableEnvironment) environment;
    }

    public static void main(String[] args) {
        System.out.println(whereIfWrapper(Hero.class));

        System.out.println(setWrapper(Hero.class));

        System.out.println(updateSet(Hero.class));

        System.out.println(updateWhereIfWrapper(Hero.class));
//        System.out.println(HeroMapper.class.getName());
//        System.out.println(insertValuesWrapper(Hero.class));

    }

}
