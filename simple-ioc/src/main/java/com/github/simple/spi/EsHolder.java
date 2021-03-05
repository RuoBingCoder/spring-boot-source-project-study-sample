package com.github.simple.spi;

import com.github.simple.core.init.SimpleInitializingBean;

/**
 * @author: jianlei.shi
 * @date: 2020/12/17 3:57 下午
 * @description: ConfigBeanTest
 */

public class EsHolder implements SimpleInitializingBean {

    private String attr;

    public EsHolder(String attr) {
        this.attr = attr;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    @Override
    public String toString() {
        return "EsHolder{" +
                "attr='" + attr + '\'' +
                '}';
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("---->>>>"+this.toString());
    }
}
