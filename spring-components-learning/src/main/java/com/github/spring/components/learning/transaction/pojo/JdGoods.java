package com.github.spring.components.learning.transaction.pojo;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JdGoods implements Serializable {

    private static final long serialVersionUID = 6306292528740356862L;
    /**
    * 主键
    */
    private Integer id;

    /**
    * 标题
    */
    private String title;

    /**
    * 标题链接
    */
    private String titleLink;

    /**
    * 缩略图
    */
    private String thumbnail;

    /**
    * 评价数
    */
    private String rate;

    /**
    * 店铺名
    */
    private String shopName;

    /**
    * 操作
    */
    private String operate;
}