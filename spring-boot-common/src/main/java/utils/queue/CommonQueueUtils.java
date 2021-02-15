package utils.queue;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author jianlei.shi
 * @date 2021/2/6 5:45 下午
 * @description CommonQueueUtils
 */

public class CommonQueueUtils<T> {

    /**
     * 得到双端队列 双链表
     *
     * @return {@link LinkedBlockingDeque<String> }
     * @author jianlei.shi
     * @date 2021-02-06 18:19:26
     */
    public static LinkedBlockingDeque<String> getLinkedBlockingDeque(){
        return  new LinkedBlockingDeque<>();
    }

    /**
     * 得到有关阻塞队列 单链表
     *
     * @return {@link LinkedBlockingQueue<String> }
     * @author jianlei.shi
     * @date 2021-02-06 18:19:24
     */
    public static LinkedBlockingQueue<String> getLinkedBlockingQueue(){
        return  new LinkedBlockingQueue<>();
    }

    public static void testLinkedBlockingDequeMethod() throws InterruptedException {
        LinkedBlockingDeque<String> linkedBlockingDeque = getLinkedBlockingDeque();
        //添加到链表尾部 不阻塞
        /**
         *
         * <code>
         *     private boolean linkLast(Node<E> node) {
         *         // assert lock.isHeldByCurrentThread();
         *         if (count >= capacity)
         *             return false;
         *         Node<E> l = last;
         *         node.prev = l;
         *         last = node;
         *         if (first == null)
         *             first = node;
         *         else
         *             l.next = node;
         *         ++count;
         *         notEmpty.signal(); //唤醒take阻塞线程
         *         return true;
         *     }
         * </code>
         *
         *
         */
        linkedBlockingDeque.add("a");

        //添加到链表尾部 阻塞
        linkedBlockingDeque.put("b");
        //出队列 头部 阻塞
        linkedBlockingDeque.take();
        //添加到链表头部 不阻塞
        linkedBlockingDeque.push("c");
        //添加到链表尾部 不阻塞
        if (linkedBlockingDeque.offer("d")) {
            System.out.println("offer success");
        }

        //出队列 头部 不阻塞
        linkedBlockingDeque.poll();
    }
    public static void testLinkedBlockingQueueMethod() throws InterruptedException {
        LinkedBlockingQueue<String> linkedBlockingQueue = getLinkedBlockingQueue();
        //添加到链表尾部 不阻塞
        linkedBlockingQueue.add("a");

        //添加到链表尾部 阻塞  last = last.next = node; 赋值依次从右到左
        linkedBlockingQueue.put("b");
        //出队列 头部 阻塞
        /**
         *
         * <code>
         *     public E take() throws InterruptedException {
         *         E x;
         *         int c = -1;
         *         final AtomicInteger count = this.count;
         *         final ReentrantLock takeLock = this.takeLock;
         *         takeLock.lockInterruptibly();
         *         try {
         *             while (count.get() == 0) {
         *                 notEmpty.await();
         *             }
         *             x = dequeue(); 这块说明队列中已经有元素
         *             c = count.getAndDecrement(); 这块主要是针对上面代码做出判断队列中元素的数量是否大于2,因为上面已经出队列c=1 即现在队列为空,不做唤醒反之则唤醒
         *             if (c > 1)
         *                 notEmpty.signal();
         *         } finally {
         *             takeLock.unlock();
         *         }
         *         if (c == capacity)
         *             signalNotFull();
         *         return x;
         *     }
         * </code>
         */
        linkedBlockingQueue.take();

        //添加到链表尾部 不阻塞
        if (linkedBlockingQueue.offer("d")) {
            System.out.println("offer success");
        }

        //出队列 头部 不阻塞
        linkedBlockingQueue.poll();
    }


    public static void moreThreadOpt() throws InterruptedException {
        LinkedBlockingQueue<String> linkedBlockingQueue = getLinkedBlockingQueue();
        Thread t1 = new Thread(()->{
            try {
                final String v = linkedBlockingQueue.take();
                System.out.println("t1-"+Thread.currentThread().getName()+"->"+v);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(()->{
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            linkedBlockingQueue.offer("a");
        });
        Thread t3 = new Thread(()->{
            try {
                final String v = linkedBlockingQueue.take();
                System.out.println("t3-"+Thread.currentThread().getName()+"->"+v);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t4 = new Thread(()->{
            linkedBlockingQueue.offer("b");
        });
        Thread t5 = new Thread(()->{
            try {
                final String v = linkedBlockingQueue.take();
                System.out.println("t5-"+Thread.currentThread().getName()+"->"+v);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
//        t1.setPriority(9);
        t1.start();
        Thread.sleep(1000);
        t2.start();
        Thread.sleep(1000);
//        t3.setPriority(8);
        t3.start();
        Thread.sleep(1000);

        t4.start();
        Thread.sleep(1000);

        t5.start();
    }

    public static void main(String[] args) throws InterruptedException {
//            testLinkedBlockingQueueMethod();
//        moreThreadOpt();
        AtomicInteger ai=new AtomicInteger();
        final int increment = ai.getAndIncrement();
        System.out.println("ai->"+ai.get()+"inc->"+increment);
        final int dc = ai.getAndDecrement();
        System.out.println("ai->"+ai.get()+"dc->"+dc);
    }
}
