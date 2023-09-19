package org.example.executor;

public interface TaskHandler {
    default void handleTaskExecution(AsyncTask task) {
        Thread thread = new Thread(() -> executeTask(task));
        thread.start();
    }
    default void executeTask(AsyncTask task){
        doCallBack(task);
        task.executor.taskMonitor.release(task);
    }

    default void doCallBack(AsyncTask task) {
        task.executor.callBack(task);
        task.latch.countDown();
    }
}
