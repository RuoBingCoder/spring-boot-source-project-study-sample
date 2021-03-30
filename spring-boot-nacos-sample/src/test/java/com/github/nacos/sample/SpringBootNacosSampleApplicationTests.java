package com.github.nacos.sample;

import com.alibaba.fastjson.JSONObject;
import com.github.http.RequestParam;
import com.github.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@Slf4j
class SpringBootNacosSampleApplicationTests {

    @Test
    void contextLoads() {
    }
    @Test
    public void postTest(){
        RequestParam requestParam=new RequestParam();
        requestParam.setSource("/test/map");
        Map<String, Object> map=new HashMap<>();
        map.put("id",1001);
        map.put("name","RuoBing");
        map.put("address","Beijing");
        requestParam.setBody(map);
        requestParam.setIsPost(true);
        final String res = HttpUtils.doPost(requestParam);
        log.info("res is :{}", JSONObject.toJSONString(res));
    }

}
