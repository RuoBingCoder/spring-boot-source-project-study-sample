package com.github.spring.components.lightweight.test.sample.job;

import com.github.spring.components.lightweight.test.sample.pojo.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jianlei.shi
 * @date 2021/2/7 2:59 下午
 * @description Task
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private int id;
    private Order order;
}
