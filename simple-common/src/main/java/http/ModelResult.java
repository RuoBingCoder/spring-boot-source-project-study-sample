package http;

import http.enums.ResultStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: jianlei.shi
 * @date: 2020/12/29 10:35 上午
 * @description: ModelResult
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModelResult<T> {
    private T data;
    private Integer code;
    private String msg;

    public static <T> ModelResult<T> success(T data) {
        return new ModelResult<>(data, ResultStatusEnum.SUCCESS.getCode(), ResultStatusEnum.SUCCESS.getMsg());
    }

    public static <T> ModelResult<T> error(String msg) {
        return new ModelResult<>(null, ResultStatusEnum.ERROR.getCode(), msg);
    }

    public static <T> ModelResult<T> success() {
        return new ModelResult<>(null, ResultStatusEnum.SUCCESS.getCode(), ResultStatusEnum.SUCCESS.getMsg());
    }
}
