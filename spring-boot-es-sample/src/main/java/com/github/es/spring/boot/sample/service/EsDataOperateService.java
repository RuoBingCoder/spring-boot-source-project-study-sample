package com.github.es.spring.boot.sample.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author: JianLei
 * @date: 2020/11/16 下午7:25
 * @description: EsOperateService
 */
public interface EsDataOperateService {
    /**
     * 查询es
     *
     * @param keyword
     * @param pageNo
     * @param pageSize
     * @return
     */
    List<Map<String, Object>> searchPageHighlight(String keyword, int pageNo, int pageSize) throws IOException;

    /**
     * 删除es数据根据id
     *
     * @param id
     * @return
     */
    Boolean deleteDataById(String id) throws IOException;

    /**
     * 更新es数据
     *
     * @param data
     * @param <T>
     * @return
     */
    <T> Boolean updateDataByObject(String id, T data) throws IOException;


    Boolean parseContents(String key) throws Exception;


}
