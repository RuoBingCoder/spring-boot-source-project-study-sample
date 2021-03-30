package com.github.spring.components.learning.enhancemybatis.common.wrapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: jianlei.shi
 * @date: 2020/12/28 2:02 下午
 * @description: QueryWrapper
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryWrapper<T> {

    private T params;
}
