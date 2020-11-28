package com.sjl.boot.autowired.inject.sample.service;

/**
 * @author: JianLei
 * @date: 2020/9/28 10:53 上午
 * @description: IMyAsync
 */
public interface IMyAsync {


    void startAsync(String task);

    void startSync(String task);
}
