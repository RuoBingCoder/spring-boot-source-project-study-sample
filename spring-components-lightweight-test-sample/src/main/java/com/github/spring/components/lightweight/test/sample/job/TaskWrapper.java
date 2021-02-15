package com.github.spring.components.lightweight.test.sample.job;

import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author jianlei.shi
 * @date 2021/2/7 2:56 下午
 * @description TaskWrapper
 */
@Component
public class TaskWrapper {

    private final LinkedBlockingQueue<Task> tasks = new LinkedBlockingQueue<>();

    public boolean offer(Task task) {
        return tasks.offer(task);
    }

    public Task take() throws InterruptedException {
        return tasks.take();
    }
}
