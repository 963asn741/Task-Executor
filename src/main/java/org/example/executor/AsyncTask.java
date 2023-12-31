package org.example.executor;

import java.util.concurrent.CountDownLatch;

public class AsyncTask {
    public TaskExecutor executor;
    public Boolean awaitTaskCompletion;
    public Long taskId;
    public Long processingTime;

    public CountDownLatch latch;

    public AsyncTask(TaskExecutor executor, Long taskId, Boolean awaitTaskCompletion, Long processingTime){
        this.executor = executor;
        this.taskId = taskId;
        this.awaitTaskCompletion = awaitTaskCompletion;
        this.processingTime = processingTime;
    }
}
