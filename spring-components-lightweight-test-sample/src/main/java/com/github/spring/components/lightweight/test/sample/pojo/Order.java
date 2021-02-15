package com.github.spring.components.lightweight.test.sample.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jianlei.shi
 * @date 2021/2/7 3:00 下午
 * @description Order
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private String name;
    private Integer nums;
    private Long id;
}
