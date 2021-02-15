package com.github.spring.components.learning.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author jianlei.shi
 * @date 2021/1/26 3:39 下午
 * @description LockCondition
 */

public class LockCondition {

    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private static final ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
    private static final Lock locks = new ReentrantLock();
    //条件队列
    private static final Condition condition = writeLock.newCondition();

    private void writeLock() {
        try {
            locks.lock();
            System.out.println("current thread get lock thread name->" + Thread.currentThread().getName());
            Thread.sleep(10000);
        } catch (Exception e) {
            throw new RuntimeException("writeLock exception");
        } finally {
            locks.unlock();
        }
    }

    //医院
    private static class Hospital {
        private String name;

        public Hospital(String name) {
            this.name = name;
        }

        //核酸检测排队测试
        public void checkUp(boolean isIdCard) {
            try {
                writeLock.lock();
                validateIdCard(isIdCard);
                System.out.println(Thread.currentThread().getName() + "正在做核酸检测");
                //核酸过程...难受...
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                writeLock.unlock();
                System.out.println(Thread.currentThread().getName() + "核酸检测完成");
            }
        }

        //校验身份信息;
        private void validateIdCard(boolean isIdCard) {
            //如果没有身份信息，需要等待
            if (!isIdCard) {
                try {
                    System.out.println(Thread.currentThread().getName() + "忘记带身份证了");
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        //通知所有等待的人
        public void singleAll() {
            try {
                writeLock.lock();
                condition.signalAll();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                writeLock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
      /*  Hospital hospital = new Hospital("南京市第一医院");
        Thread.currentThread().setName("护士小姐姐线程");
        Thread JLWife = new Thread(() -> {
            hospital.checkUp(false);
        }, "叫练妻");
        JLWife.start();
        //睡眠100毫秒是让一家三口是有顺序的排队去检测
        Thread.sleep(100);
        Thread JLSon = new Thread(() -> hospital.checkUp(true), "叫练子");
        JLSon.start();
        Thread.sleep(100);
        Thread JL = new Thread(() -> {
            hospital.checkUp(true);
        }, "叫练");
        JL.start();
        //等待叫练线程执行完毕
        JL.join();
        hospital.singleAll();*/

     /*   LockCondition lockCondition=new LockCondition();

        Thread thread1=new Thread(lockCondition::writeLock);
        thread1.start();
        Thread thread2=new Thread(lockCondition::writeLock);
        thread2.start();
        Thread thread3=new Thread(lockCondition::writeLock);
        thread3.start();
        Thread thread4=new Thread(lockCondition::writeLock);
        thread4.start();*/
        int i=1;
        int j=2;
        int k=3;
        i=j=k;
        System.out.println(i);
    }

}
