package com.sjl.spring.components.chain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jianlei.shi
 * @date 2020/12/30 5:42 下午
 * @description LeaveRequest
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveRequest {
    private String name;    // 请假人姓名
    private int numOfDays;  // 请假天数
}
