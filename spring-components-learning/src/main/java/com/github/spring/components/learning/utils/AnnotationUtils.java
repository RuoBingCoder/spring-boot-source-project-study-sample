package com.github.spring.components.learning.utils;

import cn.hutool.core.util.ObjectUtil;
import com.github.spring.components.learning.exception.MapperException;
import com.github.spring.components.learning.enhancemybatis.annoation.Table;
import lombok.extern.slf4j.Slf4j;

/**
 * @author jianlei.shi
 * @date 2020/12/30 7:09 下午
 * @description AnnotationUtils
 */
@Slf4j
public class AnnotationUtils {

    /**
     * 得到表名
     *
     * @param tyClass type类
     * @return {@link String}
     */
    public static String getTableName(Class<?> tyClass){
        Table table = tyClass.getAnnotation(Table.class);
        if (table!=null){
            String name = table.name();
            if (ObjectUtil.isNull(name)){
                throw new MapperException(tyClass+"===>类型数据库表名不能为空!");
            }
            log.info("-->>>get table name is:{}",name);
            return name;
        }
        return StringUtils.lowerUnderscore(tyClass.getSimpleName());

    }
}
