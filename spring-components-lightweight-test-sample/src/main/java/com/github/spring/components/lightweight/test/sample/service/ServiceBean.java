package com.github.spring.components.lightweight.test.sample.service;

/**
 * @author jianlei.shi
 * @date 2021/2/10 4:47 下午
 * @description ServiceBean
 */

public class ServiceBean {

    private RefService ref;

    public void setRef(RefService ref) {
        this.ref = ref;
    }

    public RefService getRef() {
        return ref;
    }

    public String refName(){
        return ref.toString();
    }
}
