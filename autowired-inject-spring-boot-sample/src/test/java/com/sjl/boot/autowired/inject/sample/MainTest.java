package com.sjl.boot.autowired.inject.sample;

import org.springframework.scheduling.support.CronTrigger;
import org.springframework.util.StringUtils;

import java.util.TimeZone;

/**
 * @author: JianLei
 * @date: 2020/9/23 7:53 下午
 * @description:
 */

public class MainTest {

  public static void main(String[] args) {
      CronTrigger cronTrig=new CronTrigger("5 * * * * *");
      String expression = cronTrig.getExpression();
    System.out.println(expression);



          String str="HelloWord";
    System.out.println(str.toLowerCase());
  }
}
