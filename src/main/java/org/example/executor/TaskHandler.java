package org.example.executor;

public interface TaskHandler {
    default void handleTaskExecution(AsyncTask task) {
        Thread thread = new Thread(() -> executeTask(task));
        thread.start();
    }
    void executeTask(AsyncTask task);

    default void doCallBack(AsyncTask task) {
        task.executor.callBack(task);
        task.latch.countDown();
    }
}
