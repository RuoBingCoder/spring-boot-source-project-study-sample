package common;

/**
 * @author jianlei.shi
 * @date 2021/2/2 7:28 下午
 * @description: TaskWrapper
 */
@FunctionalInterface
public interface TaskFunction {

    /**
     * 得到任务包装
     *
     * @param task     任务
     * @param isDaemon 是守护进程
     * @return {@link TaskWrapper }
     * @author jianlei.shi
     * @date 2021-02-02 19:32:25
     */
    TaskWrapper getTaskWrapper();

}
