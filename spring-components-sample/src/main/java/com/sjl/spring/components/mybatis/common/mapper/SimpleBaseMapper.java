package com.sjl.spring.components.mybatis.common.mapper;

import com.sjl.spring.components.mybatis.common.wrapper.QueryWrapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: JianLei
 * @date: 2020/12/26 8:08 下午
 * @description: SimpleBaseMapper
 */
public interface SimpleBaseMapper<T> {


    int insert(T entity);

    List<T> selectList(@Param("query") QueryWrapper query);

    int delete(T entity);

    int updateBySelective(@Param("entity") T entity,@Param("query") QueryWrapper query);
}
