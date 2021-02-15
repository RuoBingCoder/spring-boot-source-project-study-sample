package com.github.spring.components.lightweight.test.sample;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author jianlei.shi
 * @date 2021/2/8 2:03 下午
 * @description LockTest
 */

public class LockTest {
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    final ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    final ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
    final Semaphore semaphore = new Semaphore(10);

    private int testWriteLock() throws InterruptedException {
        writeLock.lock();
        int count = 0;
        try {
            for (int i = 0; i < 1000; i++) {
                count += i;
//                Thread.sleep(1000);
            }
            return count;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            writeLock.unlock();
        }
    }


    private int testReaderLock() {
        readLock.lock();
        int count = 0;
        try {
            for (int i = 0; i < 1000; i++) {
                count += i;
//                Thread.sleep(1000);
            }
            return count;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            readLock.unlock();
        }
    }

    /**
     * 测试信号
     *
     * @return
     * @author jianlei.shi
     * @date 2021-02-08 17:25:26
     * <code>
     *     protected int tryAcquireShared(int acquires) {
     *             return nonfairTryAcquireShared(acquires);
     *         }
     *
     *
     * </code>
     */
    public void testSemaphore() {
        try {
            semaphore.acquire();
            System.out.println("test testSemaphore");
            semaphore.release();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        final LockTest lockTest = new LockTest();
        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "---count: " + lockTest.testReaderLock());
        });
        Thread t2 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "---count: " + lockTest.testReaderLock());
        });
        Thread t3 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "---count: " + lockTest.testReaderLock());
        });
        t1.start();
        t2.start();
        t3.start();
    }
}
