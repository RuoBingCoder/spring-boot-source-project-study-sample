package com.github.spring.components.learning.lighthttp.service;

import com.github.spring.components.learning.lighthttp.annotation.Post;
import com.github.spring.components.learning.lighthttp.annotation.LightHttpClient;
import com.github.common.request.CRequestParam;

/**
 * @author jianlei.shi
 * @date 2021/2/25 11:36 上午
 * @description: LightHttpQueryService
 */
@LightHttpClient(baseUrl = "${sight.base.url}")
public interface LightHttpQueryService {


    @Post(resource = "test/getConfigs")
    String queryList(CRequestParam parameter);

    @Post(resource = "test/insert")
    String insert(CRequestParam parameter);

}
