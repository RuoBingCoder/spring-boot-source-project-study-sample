package com.github.simple.ioc.enums;

/**
 * @author: JianLei
 * @date: 2020/12/11 5:23 下午
 * @description: IocEnum
 */

public enum SimpleIOCEnum {
    BASE_PACKAGES_NOT_NULL(10000,"扫描包不能为空"),
    CLASS_TYPE_NOT_NULL(10001,"class 对象不能为空"),
    PARAMETER_NOT_NULL(10002,"参数不能为空"),
    CREATE_BEAN_ERROR(10003,"创建bean异常"),
    STATIC_FIELD_NOT_INJECT(10004,"静态字段不能依赖注入")
    ;

    private final Integer code;
    private final String msg;

    SimpleIOCEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
