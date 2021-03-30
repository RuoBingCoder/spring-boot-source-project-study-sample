package com.github.chat.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jianlei.shi
 * @date 2021/3/14 7:35 下午
 * @description CustomMessage
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomProtocol {
    private Long version;
    private String header;
    private String content;

}
