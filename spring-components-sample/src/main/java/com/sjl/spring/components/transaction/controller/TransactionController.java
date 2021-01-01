package com.sjl.spring.components.transaction.controller;

import com.sjl.spring.components.transaction.custom.annotation.EasyAutowired;
import com.sjl.spring.components.transaction.pojo.HeroDo;
import com.sjl.spring.components.transaction.pojo.TeamDo;
import com.sjl.spring.components.transaction.service.CommonOperateService;
import com.sjl.spring.components.utils.DateUtils;
import io.netty.handler.codec.http.HttpResponseStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: JianLei
 * @date: 2020/11/7 7:51 下午
 * @description: TransactionController
 */
@RestController
@RequestMapping("/transaction")
@Slf4j
public class TransactionController {


    @EasyAutowired
    private CommonOperateService<TeamDo, HeroDo> commonOperateService;

    @PostMapping(value = "/add", name = "true")
    public HttpResponseStatus addTransactionObject() {
        log.info("=====================add=======================");
//        ProgramOperateServiceImpl programOperateServiceImpl= SpringUtil.getBeanByName("commonOperateServiceProgram");
        commonOperateService.operation(getTeam(),getHero());
        return HttpResponseStatus.OK;
    }


    private HeroDo getHero() {
        return HeroDo.builder().createTime(DateUtils.getCurrentTime())
                .money(1000).name("安琪拉").type("法师").build();

    }

    private TeamDo getTeam() {
        return TeamDo.builder().createTime(DateUtils.getCurrentTime())
                .teamName("狼人杀").updateTime(DateUtils.getCurrentTime())
                .build();
    }
}
