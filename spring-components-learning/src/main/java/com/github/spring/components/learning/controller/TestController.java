package com.github.spring.components.learning.controller;

import com.github.common.request.CRequestParam;
import com.github.http.ModelResult;
import com.github.spring.components.learning.lighthttp.service.LightHttpOperateService;
import com.github.spring.components.learning.lighthttp.service.LightHttpQueryService;
import com.github.spring.components.learning.params.OrderRequestParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author jianlei.shi
 * @date 2021/1/29 2:33 下午
 * @description TestController
 */
@Api(tags = "测试控制器")
        @Controller
@RequestMapping("/test")
@Slf4j
public class TestController {


    @Resource
    private LightHttpQueryService lightHttpQueryService;
    @Resource //byName
    private LightHttpOperateService lightHttpOperateService;

    /**
     * 出口
     *
     * @param name     的名字
     * @param response 响应
     * @return
     * @author jianlei.shi
     * @date 2021-01-29 16:10:39
     * // @Aspect 注解 在拦截方法要过滤掉HttpServletResponse
     */
    @ApiOperation(value = "出口", notes = "")
    @PostMapping("/export")
    public void export(@ApiParam(value = "名字", required = true, example = "")@RequestParam("name") String name, @ApiParam(value = "响应", required = true, example = "")HttpServletResponse response) throws IOException {
        System.out.println("iiiiiiiiiiiiiiiii");
        log.info("export param is:{}", name);
//        response.getOutputStream().write("export success".getBytes());
    }

    @GetMapping("/lightGet")
    @ResponseBody
    public ModelResult<String> lightGet() {
        ModelResult<String> result = new ModelResult<String>();
        CRequestParam request = new CRequestParam();
        request.setName("WeChat");
        request.setAddress("Beijing");
        request.setId(1000L);
        final String res = lightHttpOperateService.queryList(request);
        result.setData(res);
        return result;
    }

    @GetMapping("/insert")
    @ResponseBody
    public ModelResult<String> insert() {
        ModelResult<String> result = new ModelResult<String>();
        CRequestParam request = new CRequestParam();
        request.setName("QQ");
        request.setAddress("ShangHai");
        request.setId(2000L);
        final String res = lightHttpQueryService.insert(request);
        result.setData(res);
        return result;
    }

    @GetMapping("/lightTwo")
    @ResponseBody
    public ModelResult<String> lightTwo() {
        ModelResult<String> result = new ModelResult<String>();
        CRequestParam request = new CRequestParam();
        request.setName("WeChat");
        request.setAddress("Beijing");
        request.setId(1000L);
        final String res = lightHttpOperateService.queryList(request);
        result.setData(res);
        return result;
    }

    @GetMapping("/insertTwo")
    @ResponseBody
    public ModelResult<String> insertTwo() {
        ModelResult<String> result = new ModelResult<String>();
        CRequestParam request = new CRequestParam();
        request.setName("QQ");
        request.setAddress("ShangHai");
        request.setId(2000L);
        final String res = lightHttpOperateService.insert(request);
        result.setData(res);
        return result;
    }

    @ApiOperation(value = "查询订购", notes = "")
            @GetMapping("/queryOrder")
    @ResponseBody
    public ModelResult<String> queryOrder() {
        ModelResult<String> result = new ModelResult<String>();
        OrderRequestParam orderRequestParam = new OrderRequestParam();
        orderRequestParam.setOrderId(10001L);
        orderRequestParam.setOrderName("吹风机");
        orderRequestParam.setCount(100);
        orderRequestParam.setReceiver("张三");
        orderRequestParam.setAddress("北京市海淀区上地南路");
        final ModelResult<String> modelResult = lightHttpOperateService.queryOrder(orderRequestParam);
        return modelResult;
    }
}
