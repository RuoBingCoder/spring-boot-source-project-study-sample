package com.github.es.spring.boot.sample.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.es.spring.boot.sample.service.EsDataOperateService;
import com.github.es.spring.boot.sample.service.abs.AbsEsOperate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author: JianLei
 * @date: 2020/11/16 下午7:28
 * @description: EsDataOperateServiceImpl
 */
@Service
public class EsDataOperateServiceImpl extends AbsEsOperate implements EsDataOperateService {

    @Override
    public List<Map<String, Object>> searchPageHighlight(String keyword, int pageNo, int pageSize) throws IOException {
        return searchPageHighlightBuilder(keyword, pageNo, pageSize);
    }

    @Override
    public Boolean deleteDataById(String id) throws IOException {
        return deleteObjectById(id);
    }

    @Override
    public <T> Boolean updateDataByObject(String id, T data) throws IOException {
        return updateDataById((JSONObject) data, id);
    }

    @Override
    public Boolean parseContents(String key) throws Exception {
        return parseContent(key);
    }


}
