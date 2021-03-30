package com.github.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author jianlei.shi
 * @date 2021/2/2 11:27 上午
 * @description RejectHandler
 */
@Slf4j
public class RejectHandler implements RejectedExecutionHandler {
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        log.error("current task not executor,because thread nums full,queue full");
    }
}
