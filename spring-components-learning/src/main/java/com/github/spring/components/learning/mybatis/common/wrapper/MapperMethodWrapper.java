package com.github.spring.components.learning.mybatis.common.wrapper;

import lombok.Builder;
import lombok.Data;

/**
 * @author: jianlei.shi
 * @date: 2020/12/26 10:09 下午
 * @description: MethodWrapper
 */
@Builder
@Data
public class MapperMethodWrapper {

    private String keyColumn;
    private String keyProperties;
    private String ID;
    private String sql;
    private Class<?> returnType;
    private Class<?> entityType;
    private Class<?> whereType;
}
