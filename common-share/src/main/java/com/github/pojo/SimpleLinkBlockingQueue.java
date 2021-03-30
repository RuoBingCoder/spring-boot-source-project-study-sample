package com.github.pojo;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 简单的链表阻塞队列
 *
 * @author jianlei.shi
 * @date 2021/2/6 7:20 下午
 * @description SimpleBlockingQueue
 */

public class SimpleLinkBlockingQueue<E> {
    private Node<E> last;
    private Node<E> head;
    private final ReentrantLock lock = new ReentrantLock();
    private final int capacity;
    private final AtomicInteger count = new AtomicInteger();
    private Condition condition = lock.newCondition();

    public SimpleLinkBlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    public SimpleLinkBlockingQueue() {
        this(Integer.MAX_VALUE);
        head = last = new Node<>(null);
    }

    static class Node<E> {
        private E item;
        private Node<E> next;

        public Node(E item) {
            this.item = item;
        }
    }

    public boolean offer(E e) {
        lock.lock();
        try {
            Node<E> node = new Node<>(e);
            if (count.get() < capacity) {
                addLast(node);
                count.incrementAndGet();
            } else {
                return false;
            }

        } catch (Exception ex) {
            throw new RuntimeException("offer element error!->"+ex.getMessage());
        } finally {
            lock.unlock();
        }
        return true;
    }

    public void addLast(Node<E> node) {
        last = last.next = node;

    }

    public E poll() {
        if (count.get() == 0) {
            return null;
        }
        E e = dequeue();
        count.decrementAndGet();
        return e;
    }

    private E dequeue() {
        E e;
        Node<E> first = head.next;
        head.next = head; //help GC
        e = first.item;
        first.item = null;
        head = first;
        return e;
    }

    public static void main(String[] args) throws InterruptedException {
        SimpleLinkBlockingQueue<String> sbq = new SimpleLinkBlockingQueue<>();
        sbq.condition.await();
        sbq.condition.signal();
        if (sbq.offer("a")) {
            sbq.offer("b");
        }
        System.out.println(sbq.poll());
        System.out.println(sbq.poll());
        System.out.println(sbq.poll());
    }
}
