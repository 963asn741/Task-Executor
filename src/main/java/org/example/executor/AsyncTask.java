package org.example.executor;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class AsyncTask extends Thread {
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

    public void run() {
        try {
            log.info(">>> "+ taskId +" Initiated task");
            if(this.awaitTaskCompletion){
                sleep(processingTime);
                doCallBack();
            }
            else{
                doCallBack();
                sleep(processingTime);
            }
            log.info("### "+ taskId +" Done executing");
            this.executor.taskMonitor.release(this);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void doCallBack(){
        this.latch.countDown();
        log.debug(">>> "+ taskId +" Callback called");
    }
}
