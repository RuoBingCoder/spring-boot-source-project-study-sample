package com.github.simple.core.resource;

/**
 * @author: jianlei.shi
 * @date: 2020/12/16 7:35 下午
 * @description: SimplePropteriesSource
 */

public class SimplePropertySource<T> {

    private String name;
    private T value;

    public SimplePropertySource(String name, T value) {
        this.name = name;
        this.value = value;
    }

    public SimplePropertySource() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
