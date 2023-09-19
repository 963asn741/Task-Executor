package org.example.executor;

import lombok.extern.slf4j.Slf4j;
import org.example.constants.TaskStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@Slf4j
public class MockTaskHandler implements TaskHandler{

    private Random random = new Random();

    @Value("${app.mockExecutor.causeRandomTaskFailures}")
    private Boolean causeRandomFailures;

    @Value("${app.mockExecutor.processingTimeLowerLimit}")
    private Long processingTimeLowerLimit;

    @Value("${app.mockExecutor.processingTimeUpperLimit}")
    private Long processingTimeUpperLimit;

    @Value("${app.mockExecutor.causeRandomTaskFailureProbability}")
    private Float taskFailureProbability ;

    @Override
    public void executeTask(AsyncTask task) {
        try {
            task.processingTime = random.nextLong(processingTimeLowerLimit, processingTimeUpperLimit);
            task.taskStatus = TaskStatus.INITIATED.getValue();
            log.info(">>> "+ task.taskId +" Initiated task - ETA : "+task.processingTime);
            if(task.awaitTaskCompletion){
                if(shouldTaskFail()){
                    throw new InterruptedException();
                }
                Thread.sleep(task.processingTime);
                if(task.taskStatus.equalsIgnoreCase(TaskStatus.INITIATED.getValue()))
                    task.taskStatus=TaskStatus.COMPLETED.getValue();
                doCallBack(task);
            }
            else{
                doCallBack(task);
                Thread.sleep(task.processingTime);
            }

            log.info("### "+ task.taskId +" Done executing in " + Double.valueOf(task.processingTime)/1000+" secs");
            task.executor.taskMonitor.release(task);
        } catch (InterruptedException e) {
            task.taskStatus=TaskStatus.FAILED.getValue();
            doCallBack(task);
            log.info("### "+ task.taskId +" Done executing with failure");
            task.executor.taskMonitor.release(task);
        }
    }

    private Boolean shouldTaskFail(){
        if(causeRandomFailures){
            if(taskFailureProbability>random.nextFloat(0,1))
                return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
