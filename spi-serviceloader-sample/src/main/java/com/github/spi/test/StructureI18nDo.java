package com.github.spi.test;

import lombok.Data;

import java.util.Date;

/**
 * @author jianlei.shi
 * @date 2021/3/10 11:46 上午
 * @description StructureI18nDo
 */
@Data
public class StructureI18nDo {
    /**
     * 主键
     */
    private Long id;

    /**
     * 对象apiname
     */
    private String objectApiName;

    /**
     * 类型 对象 object , 页面 layout ,字段 field , 按钮 button
     */
    private String type;

    /**
     * 项目类型  参考字典表 il8n_item_type
     */
    private String itemType;

    /**
     * 对象或字段或布局或按钮...的apiName 和类型对应
     */
    private String elementApiName;

    /**
     * 布局的apiName ,比如相同的groupApiName 在不同布局的名称不同
     */
    private String layoutApiName;

    /**
     * 国际化code
     */
    private String i18nCode;

    /**
     * 创建人
     */
    private Long creator;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人
     */
    private Long modifier;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 是否删除 1 是 2 否 默认2
     */
    private Integer isDelete;

}
