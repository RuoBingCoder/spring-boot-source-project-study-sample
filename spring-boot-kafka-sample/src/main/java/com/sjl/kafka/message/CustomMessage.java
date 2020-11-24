package com.sjl.kafka.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: JianLei
 * @date: 2020/11/19 下午7:45
 * @description: CustomMessage
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomMessage {

    private String hostname;

    private long total;

    private String msg;
}
