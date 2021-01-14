package com.github.es.spring.boot.sample.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.es.spring.boot.sample.pojo.Content;
import com.github.es.spring.boot.sample.service.EsDataOperateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @version : V1.0
 * @ClassName: ContentController
 * @Description: TODO
 * @Auther: wangqiang
 * @Date: 2020/4/22 17:12
 */
@RestController
public class ContentController {

    @Autowired
    private EsDataOperateService operateService;

    @GetMapping("/parse/{keyword}")
    public Boolean parse(@PathVariable("keyword") String keyword) throws Exception {
        return operateService.parseContents(keyword);
    }

    @GetMapping("/search/{keyword}/{pageNo}/{pageSize}")
    public List<Map<String, Object>> search(@PathVariable("keyword") String keyword,
                                            @PathVariable("pageNo") int pageNo,
                                            @PathVariable("pageSize") int pageSize) throws Exception {
        //爬取结果添加到es中
//        operateService.parseContents(keyword);
        return operateService.searchPageHighlight(keyword, pageNo, pageSize);
    }

    @GetMapping(value = "/delete", name = "contentService")
    public String delete() throws IOException {
        operateService.deleteDataById(null);
        return "success";
    }

    @PostMapping(value = "/update", name = "contentService")
    public String update() throws IOException {
        JSONObject jsonObject = JSONObject.parseObject(JSONObject.toJSONString(getContent()));
        operateService.updateDataByObject("9sX6z3UBOD0QtWM2pLcA", jsonObject);
        return "success";
    }

    public static Content getContent() {
        Content content = new Content();
        content.setShop("update-华为建极专卖店");
        return content;
    }

    public static void main(String[] args) {
        Content content = getContent();
        String str = JSONObject.toJSONString(content);
        System.out.println(str);
        JSONObject jsonObject = JSONObject.parseObject(content.toString());
        System.out.println(jsonObject);

    }
}
