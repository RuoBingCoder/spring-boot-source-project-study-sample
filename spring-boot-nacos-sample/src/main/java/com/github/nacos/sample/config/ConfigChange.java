package com.github.nacos.sample.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jianlei.shi
 * @date 2021/2/19 6:52 下午
 * @description ConfigChange
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfigChange {

    private String oldValue;
    private String newValue;




}
