package com.sjl.spring.components.mybatis.common.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: JianLei
 * @date: 2020/12/26 8:08 下午
 * @description: SimpleBaseMapper
 */
public interface SimpleBaseMapper<T,K> {


    int insert(T entity);

    List<T> selectList(T t);

    int delete(T entity);

    int updateBySelective(@Param("entity") T entity,@Param("query") K query);
}
