package http.enums;

import lombok.Getter;

/**
 * @author jianlei.shi
 * @date: 2020/12/29 11:25 上午
 * @description: ResultStatusEnum
 */
@Getter
public enum ResultStatusEnum {
   SUCCESS(200,"success"),
   ERROR(500,"系统错误"),
    ;

    private final Integer code;
    private final String msg;

    ResultStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
