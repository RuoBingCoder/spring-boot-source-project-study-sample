package utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import common.TaskFunction;
import enums.ThreadTypeEnum;
import exception.CommonException;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

import static enums.ThreadTypeEnum.ORDINARY;

/**
 * 线程池工具
 *
 * @author jianlei.shi
 * @date 2021/2/2 11:24 上午
 * @description ThreadPoolUtils
 * @see Executors.RunnableAdapter Callable
 * @see Thread#setDaemon(boolean) <class>PoolDaemonTest</class>
 * <code>
 * private Runnable getTask() {
 * boolean timedOut = false; // Did the last poll() time out?
 * <p>
 * for (;;) {
 * int c = ctl.get();
 * int rs = runStateOf(c);
 * <p>
 * // Check if queue empty only if necessary.
 * if (rs >= SHUTDOWN && (rs >= STOP || workQueue.isEmpty())) {
 * decrementWorkerCount();
 * return null;
 * }
 * <p>
 * int wc = workerCountOf(c);
 * <p>
 * // Are workers subject to culling?
 * boolean timed = allowCoreThreadTimeOut || wc > corePoolSize;
 * <p>
 * if ((wc > maximumPoolSize || (timed && timedOut))
 * && (wc > 1 || workQueue.isEmpty())) {
 * if (compareAndDecrementWorkerCount(c))
 * return null; //🚩线程中断 返回null.执行清除线程逻辑
 * continue;
 * }
 * <p>
 * try {
 * Runnable r = timed ?
 * workQueue.poll(keepAliveTime, TimeUnit.NANOSECONDS) :
 * workQueue.take(); //队列为空线程池阻塞; 🚩 <theReason>所以自己实现Thread 运行完就会停止 而线程池不会</theReason>
 * if (r != null)
 * return r;
 * timedOut = true;
 * } catch (InterruptedException retry) { //当线程中断阻塞结束
 * timedOut = false;
 * }
 * }
 * }
 * </code>
 */
@Slf4j
public class ThreadPoolUtils {
    public static String format = "common-pool-thread-%s";

    /**
     * 得到执行器
     *
     * @param isSchedule 是否是定时任务
     * @return {@link ThreadPoolExecutor }
     * @author jianlei.shi
     * @date 2021-02-02 11:40:28
     * @see ThreadTypeEnum
     */
    public static ThreadPoolExecutor getExecutor(ThreadTypeEnum type, boolean isDaemon) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 8, 20, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), getThreadFactory(isDaemon), new RejectHandler());
        switch (type) {
            case SCHEDULED:
                return new ScheduledThreadPoolExecutor(4, getThreadFactory(isDaemon), new RejectHandler());
            case CACHE:
                return new ThreadPoolExecutor(4, 8, 20, TimeUnit.MILLISECONDS, new SynchronousQueue<>(), getThreadFactory(isDaemon), new RejectHandler());
            default:
                return threadPoolExecutor;
        }


    }

    private static ThreadFactory getThreadFactory(boolean isDaemon) {
        return new ThreadFactoryBuilder().setDaemon(isDaemon).setNameFormat(format).build();
    }

    /**
     * 执行
     *
     * @param task 任务
     * @author jianlei.shi
     * @date 2021-02-02 11:40:24
     * <code>
     * public void shutdown() {
     * final ReentrantLock mainLock = this.mainLock;
     * mainLock.lock();
     * try {
     * checkShutdownAccess();
     * advanceRunState(SHUTDOWN);
     * interruptIdleWorkers(); //🚩中断空闲的线程,正在执行的线程继续执行
     * onShutdown(); // hook for ScheduledThreadPoolExecutor
     * } finally {
     * mainLock.unlock();
     * }
     * tryTerminate();
     * }
     * </code>
     * <code>
     * <p>
     * public List<Runnable> shutdownNow() {
     * List<Runnable> tasks;
     * final ReentrantLock mainLock = this.mainLock;
     * mainLock.lock();
     * try {
     * checkShutdownAccess();
     * advanceRunState(STOP);
     * interruptWorkers(); //🚩中断所有线程
     * tasks = drainQueue();
     * } finally {
     * mainLock.unlock();
     * }
     * tryTerminate();
     * return tasks;
     * }
     * </code>
     */
    public static ThreadPoolExecutor execute(TaskFunction function) {
        ThreadPoolExecutor executor = null;
        try {
            executor = getExecutor(ORDINARY, function.getTaskWrapper().isDaemon());
            executor.execute(function.getTaskWrapper().getTask());
            return executor;
        } catch (Exception e) {
            log.error("execute error", e);
            assert executor != null;
            /**
             *
             * <code>
             *      private void interruptIdleWorkers(boolean onlyOne) {
             *         final ReentrantLock mainLock = this.mainLock;
             *         mainLock.lock();
             *         try {
             *             for (Worker w : workers) {
             *                 Thread t = w.thread;
             *                 if (!t.isInterrupted() && w.tryLock()) { 🚩🚩 🚩  <p>w.tryLock()重要 不可重入,一旦线程正在运行,则是已经获取到锁了,此时再去获取就会失败 ReentrantLock tryAcquire()可重入</p>
             *                     try {
             *                         t.interrupt();
             *                     } catch (SecurityException ignore) {
             *                     } finally {
             *                         w.unlock();
             *                     }
             *                 }
             *                 if (onlyOne)
             *                     break;
             *             }
             *         } finally {
             *             mainLock.unlock();
             *         }
             *     }
             * </code>
             *
             *
             *
             */
            executor.shutdown();
            throw CommonException.getInstance("execute error->" + e.getMessage());
        }

    }

    /**
     * 提交
     *
     * @param task 任务
     * @return {@link T }
     * @author jianlei.shi
     * @date 2021-02-02 11:40:20
     */
    public static <T> T submit(TaskFunction function) {
        ThreadPoolExecutor executor = null;
        try {
            executor = getExecutor(ORDINARY, function.getTaskWrapper().isDaemon());
            return (T) executor.submit(function.getTaskWrapper().getTask());
        } catch (Exception e) {
            log.error("submit error", e);
            assert executor != null;
            executor.shutdown();
//            executor.shutdownNow();
            throw CommonException.getInstance("submit error->" + e.getMessage());
        }

    }

    /**
     * 执行定时计划
     *
     * @param task 任务
     * @return
     * @author jianlei.shi
     * @date 2021-02-02 11:40:09
     */
    public static void executeSchedule(TaskFunction function) {
        ScheduledThreadPoolExecutor executor = null;
        try {
            executor = (ScheduledThreadPoolExecutor) getExecutor(ThreadTypeEnum.SCHEDULED, function.getTaskWrapper().isDaemon());
            executor.scheduleAtFixedRate(function.getTaskWrapper().getTask(), 2, 1, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("executeSchedule error", e);
            assert executor != null;
            executor.shutdown();
            throw CommonException.getInstance("executeSchedule error->" + e.getMessage());
        }
    }

    /**
     * 得到同步队列
     *
     * @return {@link SynchronousQueue }
     * @author jianlei.shi
     * @date 2021-02-03 14:18:38
     * put() take() 源码分析
     * <code>
     * <method>put()</method>
     * SNode s = null; // constructed/reused as needed
     * int mode = (e == null) ? REQUEST : DATA; 此处mode=1
     * <p>
     * for (;;) {
     * SNode h = head;
     * if (h == null || h.mode == mode) {  // empty or same-mode
     * if (timed && nanos <= 0) {      // can't wait
     * if (h != null && h.isCancelled())
     * casHead(h, h.next);     // pop cancelled node
     * else
     * return null;
     * } else if (casHead(h, s = snode(s, e, h, mode))) {
     * <p>
     * SNode m = awaitFulfill(s, timed, nanos); //阻塞当前线程
     * if (m == s) {               // wait was cancelled
     * clean(s);
     * return null;
     * }
     * if ((h = head) != null && h.next == s)
     * casHead(h, s.next);     // help s's fulfiller
     * return (E) ((mode == REQUEST) ? m.item : s.item);
     * }
     * }
     * //....省略部分代码
     * }
     *
     * </code>
     *
     * <code>
     * <method>take()</method>
     * SNode s = null; // constructed/reused as needed
     * int mode = (e == null) ? REQUEST : DATA;
     * <p>
     * for (;;) {
     * SNode h = head;
     * if (h == null || h.mode == mode) {  // empty or same-mode
     * //....
     * } else if (!isFulfilling(h.mode)) { // try to fulfill 为false
     * if (h.isCancelled())            // already cancelled
     * casHead(h, h.next);         // pop and retry
     * else if (casHead(h, s=snode(s, e, h, FULFILLING|mode))) { //🚩此处采用头插法 Node()
     * <pre>
     *                           static SNode snode(SNode s, Object e, SNode next, int mode) {
     *                                 if (s == null) s = new SNode(e); 即
     *                                     s.mode = mode;
     *                                     s.next = next;
     *                                     return s;
     *         }
     *                     </pre>
     * for (;;) { // loop until matched or waiters disappear
     * SNode m = s.next;       // m is s's match
     * if (m == null) {        // all waiters are gone
     * casHead(s, null);   // pop fulfill node
     * s = null;           // use new node next time
     * break;              // restart main loop
     * }
     * SNode mn = m.next;
     * if (m.tryMatch(s)) { //释放锁
     * casHead(s, mn);     // pop both s and m
     * return (E) ((mode == REQUEST) ? m.item : s.item);
     * } else                  // lost match
     * s.casNext(m, mn);   // help unlink
     * }
     * }
     * </code>
     * <p>TransferStack 尾插法</p>
     * <p>TransferQueue 头插法</p>
     */
    public SynchronousQueue getSynchronousQueue() {
        return new SynchronousQueue();
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolExecutor executor = getExecutor(ORDINARY, false);
        final Future<Integer> submit = executor.submit(() -> 1 + 2);
        System.out.println(submit.get());

    }

}
