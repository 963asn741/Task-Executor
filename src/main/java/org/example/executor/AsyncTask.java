package org.example.executor;

import java.util.concurrent.CountDownLatch;

public class AsyncTask {
    public TaskExecutor executor;
    public Boolean awaitTaskCompletion;
    public Long taskId;
    public String reqId;
    public String taskStatus;
    public Long processingTime;
    public CountDownLatch latch;

    public AsyncTask(TaskExecutor executor, Long taskId, Boolean awaitTaskCompletion, String reqId){
        this.executor = executor;
        this.taskId = taskId;
        this.awaitTaskCompletion = awaitTaskCompletion;
        this.reqId = reqId;
    }
}
