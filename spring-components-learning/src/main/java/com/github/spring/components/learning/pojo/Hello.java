package com.github.spring.components.learning.pojo;

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

    public static void main(String[] args) {
        Hello hello_1 = new Hello("a");
        Hello hello_2 = hello_1;
        System.out.println(hello_1 == hello_1);
        hello_2.setMsg("b");

        System.out.println("hello_1: " + hello_1);
        System.out.println("hello_2: " + hello_2);
    }

}
