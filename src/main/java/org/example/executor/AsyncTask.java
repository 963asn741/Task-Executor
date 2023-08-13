package org.example.executor;


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class AsyncTask extends Thread {
    public TaskExecutor executor;
    public Boolean awaitTaskCompletion;
    public Long taskId;

    public CountDownLatch latch;

    public AsyncTask(TaskExecutor executor, Long taskId, Boolean awaitTaskCompletion){
        this.executor = executor;
        this.taskId = taskId;
        this.awaitTaskCompletion = awaitTaskCompletion;
    }

    public void run() {
        try {
            log.debug(">>> "+System.currentTimeMillis()+" "+ taskId +" Initiated task");
            if(this.awaitTaskCompletion){
                sleep(5000);
                doCallBack();

            }
            else{
                doCallBack();
                sleep(5000);
            }
            log.debug(">>> "+System.currentTimeMillis()+" "+ taskId +" Done executing");
            this.executor.taskMonitor.release();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void doCallBack(){
        this.latch.countDown();
        log.debug(">>> "+System.currentTimeMillis()+" "+ taskId +" Callback called");
    }
}
