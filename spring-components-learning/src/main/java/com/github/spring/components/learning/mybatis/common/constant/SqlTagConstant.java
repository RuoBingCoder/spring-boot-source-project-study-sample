package com.github.spring.components.learning.mybatis.common.constant;

/**
 * @author: jianlei.shi
 * @date: 2020/12/27 4:49 下午
 * @description: SqlTagConstant
 */

public class SqlTagConstant {

    public static final String DAO_SCAN_PACKAGES_PREFIX = "dao.scan.packages";
    public static final String LEFT_BRACKETS = "(";
    public static final String RIGHT_BRACKETS = ")";
    public static final String SEGMENTATION = ",";
    public static final String WHERE_TAG_START = "<where>\n";
    public static final String WHERE_NO_TAG = "where  \n";
    public static final String IF_START_TAG = "<if test=\" %s !=null and %s !=''\"> and ";
    public static final String IF_NON_AND_START_TAG = "<if test=\" %s !=null and %s !=''\">";
    public static final String IF_END_TAG = "</if>\n";
    public static final String WHERE_END_TAG = "\n</where>\n";
    public static final String LEFT_PLACEHOLDER = "#{";
    public static final String RIGHT_PLACEHOLDER = "}";
    public static final String LEFT_EQ_PLACEHOLDER = "=#{";
    public static final String SET_START_TAG = "<set>";
    public static final String SET_END_TAG = "</set>";
    public static final String STAR_SYMBOL = "*";
    public static final String KEY_COLUMN = "id";
    public static final String KEY_PROPERTY = "id";
    public static final String PARAMS_PREFIX = "entity.";
    public static final String QUERY = "query";
    public static final String POINT = ".";
    public static final String QUERY_PARAMS_PREFIX = "query.params.";
    public static final String SQL_UPDATE_OPERATE = "update";
    public static final String SQL_INSERT_OPERATE = "insert";
    public static final String PRIMARY_KEY = "id";


}
