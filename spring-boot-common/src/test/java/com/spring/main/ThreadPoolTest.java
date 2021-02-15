package com.spring.main;

import common.TaskWrapper;
import utils.ThreadPoolUtils;

/**
 * @author jianlei.shi
 * @date 2021/2/2 11:41 上午
 * @description ThreadPoolTest
 */

public class ThreadPoolTest {

    public static void poolRun() {

        for (int i = 0; i < 20; i++) {
            ThreadPoolUtils.execute(() -> {
                try {

                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return TaskWrapper.getInstance(() -> System.out.println("hello word"), false);
            });
        }
    }


    public static void main(String[] args) throws InterruptedException {
      /*  ThreadPoolTest.poolRun();
       Thread thread = new Thread(() -> System.out.println("thread start"));
        thread.setDaemon(false);
        thread.setPriority(1); //设置优先级
        thread.start();
        int[] ins=new int[]{};
        String[] strings=new String[]{};*/
       /* SynchronousQueue<String> sq = new SynchronousQueue<>();
        ThreadPoolUtils.execute(() -> TaskWrapper.getInstance(() -> {
            try {
                sq.put("a");
//                sq.put("b");
//                System.out.println("a : "+sq.take());
                System.out.println("------>a 结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, true));
        ThreadPoolUtils.execute(() -> TaskWrapper.getInstance(() -> {
            try {
                sq.put("b");
//                sq.put("b");
//                System.out.println("a : "+sq.take());
                System.out.println("------>b 结束");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, true));
        ThreadPoolUtils.execute(() -> TaskWrapper.getInstance(() -> {
            try {
//                sq.put("b");
//                Thread.sleep(1000);
                int i = 0;
                while (i < 2) {
                    System.out.println("t2 : " + sq.take());
                    i++;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, true));

        System.out.println("============================main end!================================");

    }*/
    }
}
