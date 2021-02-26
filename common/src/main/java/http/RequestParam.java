package http;

import lombok.Data;

import java.util.Map;

/**
 * @author jianlei.shi
 * @date 2021/2/16 7:08 下午
 * @description RequestParam
 */
@Data
public class RequestParam {

    private String baseUrl;

    private String param;

    private Class<?> type;

    /**
     * 源 to Get
     */
    private String source;

    /**
     * body to Post
     */
    private Map<String, Object> body;


    private Boolean isPost = false;


}
