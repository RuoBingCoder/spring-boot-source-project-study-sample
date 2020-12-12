package com.sjl.spring.components.pojo;

import lombok.Builder;
import lombok.Data;

/**
 * @author: JianLei
 * @date: 2020/12/6 下午2:21
 * @description: GeoHolder
 */
@Data
@Builder
public class GeoHolder {

    private String key;
    private Double x;
    private Double y;
    private String address;
}
