package com.sjl.spring.components.controller;

import com.sjl.spring.components.transaction.pojo.Hero;
import com.sjl.spring.components.transaction.service.HeroService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author: JianLei
 * @date: 2020/11/30 下午7:35
 * @description: TxSourceController
 */
@RestController
@RequestMapping("/tx")
@Slf4j
public class TxSourceController {


    @Resource
    private HeroService heroService;


    @GetMapping(value = "/getHero")
    public String getHero() {
        heroService.insert(Hero.builder().createTime(LocalDateTime.now()).money(122).name("sunshangxiang").type("刺客").build());
        return "success";
    }
}
