package com.github.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.github.exception.CommonException;
import com.github.http.RequestParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import java.util.Map;
import java.util.Objects;

/**
 * @author jianlei.shi
 * @date 2021/2/16 7:07 下午
 * @description HttpUtils
 */
@Slf4j
public class HttpUtils implements EnvironmentAware, InitializingBean {
    private Environment environment;
    /**
     * 需要在application.yml ｜ application.properties 配置 http:url
     */
    private static String url;


    /**
     * get 请求
     *
     * @param rp rp
     * @return {@link String }
     * @author jianlei.shi
     * @date 2021-02-20 15:32:35
     */
    public static String doGet(RequestParam rp) {
        try {
            for (int i = 0; i < 3; i++) {
                final String res = HttpUtil.get(getUrl(rp), 2000);
                if (isNull(res)) {
                    continue;
                }
                return res;
            }
            return enums.HttpEnum.SEND_MESSAGE_FAILED.getType();
        } catch (Exception e) {
            log.error("doGet error msg :" + e.getMessage());
            throw new RuntimeException("doGet error!");
        }

    }

    private static boolean isNull(Object result) {
        return result == null || "".equals(result);
    }

    private static String getUrl(RequestParam rp) {
        if (Objects.isNull(rp)) {
            throw new RuntimeException("RequestParam param is not null");
        }
        checkUrl(rp);
        final String param = rp.getParam();
        final String source = rp.getSource();
        if (url == null) {
            url = rp.getBaseUrl();
        }
        return rp.getIsPost() ? url + source : url + source + param;


    }

    private static void checkUrl(RequestParam rp) {
        if (rp.getBaseUrl() != null) {
            return;
        }
        if (url == null || "".equals(url)) {
            throw new CommonException("Please check if the url is configured");
        }
    }


    /**
     * post 请求
     *
     * @param rq 中移动
     * @return {@link String }
     * @author jianlei.shi
     * @date 2021-02-20 15:32:50
     */
    public static String doPost(RequestParam rq) {
        try {
            for (int i = 0; i < 3; i++) {
                String res = HttpRequest.post(getUrl(rq)).header(Header.CONTENT_TYPE, enums.HttpEnum.CONTENT_TYPE_JSON.getType()).body(toString(getBody(rq))).execute().body();
                if (isNull(res)) {
                    continue;
                }
                return res;
            }
            return enums.HttpEnum.SEND_MESSAGE_FAILED.getType();

        } catch (Exception e) {
            log.error("doPost error!", e);
            throw new CommonException("doPost error");
        }

    }

    private static String toString(Map<String, Object> body) {
        return JSONObject.toJSONString(body);
    }

    private static Map<String, Object> getBody(RequestParam rq) {

        final Map<String, Object> body = rq.getBody();
        if (CollectionUtil.isEmpty(body)) {
            throw new CommonException("post request body not null!");
        }
        return body;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        /* if (url == null || "".equals(url)) {
            throw new CommonException("no such http url , Please check if the url is configured");
        }*/
        //获取配置http.url
        HttpUtils.url = environment.getProperty(enums.HttpEnum.HTTP_CONFIG_PREFIX.getType());
    }
}
