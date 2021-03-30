package com.github.simple.demo.service.config.domain;

/**
 * @author: jianlei.shi
 * @date: 2020/12/17 3:57 下午
 * @description: ConfigBeanTest
 */

public class MethodBean2 {

    private String info;
    private Long id;

    public MethodBean2(String info, Long id) {
        this.info = info;
        this.id = id;
    }

    public MethodBean2() {
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
