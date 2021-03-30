package com.github.dubbo.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author jianlei.shi
 * @date 2021/3/19 10:53 上午
 * @description BaseQueryParam
 */
@Data
public class BaseQueryParam implements Serializable {

    private static final long serialVersionUID = 8035313586110034836L;

    private Long id;
    private String name;
}
