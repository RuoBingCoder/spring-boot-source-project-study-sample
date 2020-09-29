package com.sjl.custom.aop.service;

import com.sjl.custom.aop.annotation.Cglib;

/**
 * @author: JianLei
 * @date: 2020/9/29 11:08 上午
 * @description: CglibService
 */
@Cglib
public class CglibService {

  public void testCglibService() {
    System.out.println("test cglib service!");
  }


  public String selectOrderById(Integer id){
    return "获取订单详情参数为"+id;
  }
}
