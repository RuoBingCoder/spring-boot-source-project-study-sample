package utils;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import common.TaskFunction;
import enums.ThreadTypeEnum;
import exception.CommonException;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

import static enums.ThreadTypeEnum.CACHE;
import static enums.ThreadTypeEnum.ORDINARY;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

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
     * 得到默认的执行器
     *
     * @return {@link ThreadPoolExecutor }
     * @author jianlei.shi
     * @date 2021-02-20 15:51:20
     */
    public static ThreadPoolExecutor getDefaultExecutor() {
        return getExecutor(ORDINARY, true);
    }

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
                return new ThreadPoolExecutor(1, 3, 2, TimeUnit.SECONDS, new SynchronousQueue<>(), getThreadFactory(isDaemon), new RejectHandler());
            default:
                return threadPoolExecutor;
//               Executors.newCachedThreadPool()
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
            if (executor.getMaximumPoolSize() < executor.getActiveCount()) {
                log.info("ThreadPoolExecutor thread active count: {} current thread count > max ", executor.getActiveCount());

            }
            return (T) executor.submit(function.getTaskWrapper().getTask());
        } catch (Exception e) {
            log.error("submit error", e);
            assert executor != null;
            executor.shutdown();
//            executor.shutdownNow();
            throw CommonException.getInstance("submit error->" + e.getMessage());
        }

    }

    public static <T> T submit(Runnable task) {
        ThreadPoolExecutor executor = null;
        try {
            executor = getExecutor(CACHE, true);
            if (executor.getMaximumPoolSize() < executor.getActiveCount()) {
                log.info("ThreadPoolExecutor thread active count: {} current thread count > max ", executor.getActiveCount());

            }
            return (T) executor.submit(task);
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
     * @see ScheduledThreadPoolExecutor.ScheduledFutureTask#run()
     */
    public static void executeTaskAtFixedRate(Runnable task) {
        ScheduledThreadPoolExecutor executor = null;
        try {
            executor = (ScheduledThreadPoolExecutor) getExecutor(ThreadTypeEnum.SCHEDULED, false);

            //固定执行频率 不受任务执行时间影响 12:00 12:02 12:04
            /**
             * <p>scheduleAtFixedRate 和 scheduleWithFixedDelay区别在此</p>
             * <code>
             *      private void setNextRunTime() {
             *             long p = period;
             *             if (p > 0)
             *                 time += p;
             *             else
             *                 time = triggerTime(-p);
             *         }
             * </code>
             *
             * <code>
             *     public long getDelay(TimeUnit unit) {
             *             return unit.convert(time - now(), NANOSECONDS);
             *         }
             *
             *
             *       public RunnableScheduledFuture<?> take() throws InterruptedException {
             *             final ReentrantLock lock = this.lock;
             *             lock.lockInterruptibly();
             *             try {
             *                 for (;;) {
             *                     RunnableScheduledFuture<?> first = queue[0];
             *                     if (first == null)
             *                         available.await();
             *                     else {
             *                         long delay = first.getDelay(NANOSECONDS);
             *                         if (delay <= 0) //小于0直接执行 see <method>getTask()</method>
             *                             return finishPoll(first);
             *                         first = null; // don't retain ref while waiting
             *                         if (leader != null)
             *                             available.await();
             *                         else {
             *                             Thread thisThread = Thread.currentThread();
             *                             leader = thisThread;
             *                             try {
             *                                 available.awaitNanos(delay); //等待设置的周期
             *                             } finally {
             *                                 if (leader == thisThread)
             *                                     leader = null;
             *                             }
             *                         }
             *                     }
             *                 }
             *             } finally {
             *                 if (leader == null && queue[0] != null)
             *                     available.signal();
             *                 lock.unlock();
             *             }
             *         }
             * </code>
             *
             *
             *
             * 378168581486402
             */
            executor.scheduleAtFixedRate(task, 2, 4, TimeUnit.SECONDS);
            Thread.sleep(2000);
            log.info("schedule thread active count: {}", executor.getActiveCount()); //用于监控
            if (executor.getMaximumPoolSize() < executor.getActiveCount()) {
                log.warn("ThreadPoolExecutor thread active count: {} current thread count > max ", executor.getActiveCount());

            }
        } catch (Exception e) {
            log.error("executeSchedule error", e);
            assert executor != null;
            executor.shutdown();
            throw CommonException.getInstance("executeSchedule error->" + e.getMessage());
        }
    }

    /**
     * 执行定时计划
     *
     * @param task 任务
     * @return
     * @author jianlei.shi
     * @date 2021-02-02 11:40:09
     * @see ScheduledThreadPoolExecutor.ScheduledFutureTask#run()
     */
    public static void executeTaskWithFixedDelay(Runnable task) {
        ScheduledThreadPoolExecutor executor = null;
        try {
            executor = (ScheduledThreadPoolExecutor) getExecutor(ThreadTypeEnum.SCHEDULED, false);
            //受执行任务的时间影响 比如 任务 12:00  执行 12:12 执行完 那么下一次执行时间就是12:12+delay 执行
            executor.scheduleWithFixedDelay(task, 2, 4, TimeUnit.SECONDS);
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
    public static SynchronousQueue getSynchronousQueue() {
        return new SynchronousQueue();
    }

    public static long now() {
        return System.nanoTime();
    }

    public static long triggerTime(long delay) {
        delay = delay < 0 ? -delay : delay;
        long nanosDelay = delayTime(delay);
        System.out.println((nanosDelay < (Long.MAX_VALUE >> 1)));
        return now() +
                ((nanosDelay < (Long.MAX_VALUE >> 1)) ? nanosDelay : 0L);
    }

    public static long delayTime(long delay) {
        return TimeUnit.SECONDS.toNanos(delay);
    }

    public static long getDelay(TimeUnit unit, long time) {
        return unit.convert(time - now(), NANOSECONDS);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//       executeTaskAtFixedRate(()-> System.out.println("time is: "+System.currentTimeMillis()/1000));
        executeTaskAtFixedRate(() -> {
            System.out.println("Start time is: " + System.currentTimeMillis() / 1000);

//            System.out.println("End time is: " + System.currentTimeMillis() / 1000);
        });
       /* executeTaskWithFixedDelay(()-> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            System.out.println("time is: "+System.currentTimeMillis()/1000);
        }
        );*/
        System.out.println(triggerTime(-2));
        System.out.println(delayTime(2));
        System.out.println(getDelay(TimeUnit.SECONDS, delayTime(2)));
        getSynchronousQueue().put("a"); //只能存放一个元素只针对当前线程
    }


}
