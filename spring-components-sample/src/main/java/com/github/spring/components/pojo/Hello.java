package com.github.spring.components.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: JianLei
 * @date: 2020/11/20 上午11:17
 * @description: Hello
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Hello {
    private String msg;

}
