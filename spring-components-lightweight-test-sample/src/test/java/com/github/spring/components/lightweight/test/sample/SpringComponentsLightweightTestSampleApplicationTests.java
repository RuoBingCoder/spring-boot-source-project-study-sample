package com.github.spring.components.lightweight.test.sample;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.locks.ReentrantReadWriteLock;

@SpringBootTest
class SpringComponentsLightweightTestSampleApplicationTests {

    private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    final ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    final ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

    void testReaderLock() {
        readLock.lock();
        try {
            int count = 0;
            for (int i = 0; i <1000 ; i++) {
                count+=i;
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           readLock.unlock();
        }
    }

    @Test
    void contextLoads() {
    }

    public static void main(String[] args) {
        System.out.println(65536 & 65535);

    }

}
