package org.example.executor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MockTaskHandler implements TaskHandler{

    @Override
    public void handleTaskExecution(AsyncTask task) {
        log.debug("EXEC -> handleTaskExecution()");
        Thread thread = new Thread(() -> {
            try {
                log.info(">>> "+ task.taskId +" Initiated task");
                if(task.awaitTaskCompletion){
                    Thread.sleep(task.processingTime);
                    doCallBack(task);
                }
                else{
                    doCallBack(task);
                    Thread.sleep(task.processingTime);
                }
                log.info("### "+ task.taskId +" Done executing");
                task.executor.taskMonitor.release(task);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        thread.start();
    }

    private void doCallBack(AsyncTask task){
        task.latch.countDown();
        log.debug(">>> "+ task.taskId +" Callback called");
    }
}
