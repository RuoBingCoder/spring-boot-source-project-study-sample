package exception;

/**
 * @author jianlei.shi
 * @date 2021/2/2 11:32 上午
 * @description CommonException
 */

public class CommonException extends RuntimeException {
    public CommonException(String message) {
        super(message);
    }

    public static CommonException getInstance(String message) {
        return new CommonException(message);
    }
}
