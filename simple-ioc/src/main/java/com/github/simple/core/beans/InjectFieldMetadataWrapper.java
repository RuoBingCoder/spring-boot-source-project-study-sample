package com.github.simple.core.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;

/**
 * @author: jianlei.shi
 * @date: 2020/12/21 11:00 上午
 * @description: InjectFieldMetadataWrapper
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class InjectFieldMetadataWrapper {

    private String fieldName;

    private Field field;




}
