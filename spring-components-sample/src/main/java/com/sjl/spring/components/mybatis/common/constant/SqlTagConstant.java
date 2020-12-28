package com.sjl.spring.components.mybatis.common.constant;

/**
 * @author: jianlei.shi
 * @date: 2020/12/27 4:49 下午
 * @description: SqlTagConstant
 */

public class SqlTagConstant {

    public static final String DAO_SCAN_PACKAGES_PREFIX = "dao.scan.packages";
    public static final String LEFT_PARENTHESIS = "(";
    public static final String RIGHT_PARENTHESIS = ")";
    public static final String SEGMENTATION = ",";
    public static final String WHERE_START = "<where>\n";
    public static final String WHERE = "where  \n";
    public static final String IF_TAG_S = "<if test=\" %s !=null and %s !=''\"> and ";
    public static final String IF_TAG_NA_S = "<if test=\" %s !=null and %s !=''\">";
    public static final String IF_TAG_E = "</if>\n";
    public static final String WHERE_END = "\n</where>\n";
    public static final String LEFT_PLACE_HOLDER = "#{";
    public static final String RIGHT_PLACE_HOLDER = "}";
    public static final String LEFT_EQ_PLACE_HOLDER = "=#{";
    public static final String SET_START_TAG = "<set>";
    public static final String SET_END_TAG = "</set>";
    public static final String STAR_SYMBOL = "*";
    public static final String KEY_COLUMN = "id";
    public static final String KEY_PROPERTY = "id";
    public static final String ET = "entity";
    public static final String QUERY = "query";
    public static final String POINT = ".";


}
