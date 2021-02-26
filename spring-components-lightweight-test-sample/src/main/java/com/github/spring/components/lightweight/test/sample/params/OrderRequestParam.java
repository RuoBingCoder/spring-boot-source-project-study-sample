package com.github.spring.components.lightweight.test.sample.params;

import lombok.Data;

/**
 * @author jianlei.shi
 * @date 2021/2/26 10:24 上午
 * @description OrderRequestParam
 */
@Data
public class OrderRequestParam {

    private Long orderId;
    private String orderName;
    private Integer count;
    private String receiver;
    private String address;

}
