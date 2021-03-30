package com.github.spring.components.learning.pojo;

/**
 * 注释豆
 *
 * @author shijianlei
 * @date: 2020/12/4 下午6:40
 * @description: AnnotationBean
 * @date 2021-03-10 11:36:28
 */

public class AnnotationBean {

    /**
     * 的名字
     */
    private String name;

    /**
     * 得到的名字
     *
     * @return {@link String }
     * @author jianlei.shi
     * @date 2021-03-10 11:36:28
     */
    public String getName() {
        return name;
    }

    /**
     * 集名称
     *
     * @param name 的名字
     * @return
     * @author jianlei.shi
     * @date 2021-03-10 11:36:28
     */
    public void setName(String name) {
        this.name = name;
    }
}
