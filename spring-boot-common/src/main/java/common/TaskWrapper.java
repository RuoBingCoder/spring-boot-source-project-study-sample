package common;

/**
 * @author jianlei.shi
 * @date 2021/2/2 7:30 下午
 * @description TaskWrapper
 */

public class TaskWrapper {
    private Runnable task;
    private boolean isDaemon;

    public TaskWrapper(Runnable task, boolean isDaemon) {
        this.task = task;
        this.isDaemon = isDaemon;
    }

    public Runnable getTask() {
        return task;
    }

    public void setTask(Runnable task) {
        this.task = task;
    }

    public boolean isDaemon() {
        return isDaemon;
    }

    public void setDaemon(boolean daemon) {
        isDaemon = daemon;
    }
    public static TaskWrapper getInstance(Runnable task, boolean isDaemon){
        return new TaskWrapper(task, isDaemon);
    }
}
