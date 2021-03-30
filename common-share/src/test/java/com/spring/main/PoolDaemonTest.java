package com.spring.main;

/**
 * @author jianlei.shi
 * @date 2021/2/2 2:47 下午
 * @description PoolDaemon
 */

public class PoolDaemonTest {

        public static void main(String[] args) {
            new WorkerThread().start();

            try {
                Thread.sleep(7500);
            } catch (InterruptedException e) {
                // handle here exception
            }

            System.out.println("Main Thread ending") ;
        }

    }

    class WorkerThread extends Thread {

        public WorkerThread() {
            // When false, (i.e. when it's a non daemon thread),
            // the WorkerThread continues to run.
            // When true, (i.e. when it's a daemon thread),
            // the WorkerThread terminates when the main
            // thread or/and user defined thread(non daemon) terminates.
            setDaemon(true); //设置为true当main线程结束 run方法死循环将结束 false则反之
        }

        public void run() {
            int count = 0;

            while (true) {
                System.out.println("Hello from Worker "+count++);

                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    // handle exception here
                }
            }
        }
}
