package org.example.executor;

import org.example.vo.TaskRequestVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component("customTaskMonitor")
public class TaskMonitor {
    @Value("${app.max_concurrent_threads}")
    private Integer MAX_THREAD_COUNT;
    private Integer currentCount = 0;
    private ConcurrentLinkedQueue<AsyncTask> taskQueue = new ConcurrentLinkedQueue<>();

    protected synchronized void acquire(AsyncTask task){
        if(!isAvailable()){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        currentCount++;
        taskQueue.add(task);
    }

    protected synchronized void release(AsyncTask task){
        if(currentCount>0){
            taskQueue.remove(task);
            currentCount--;
            notifyAll();
        }
    }

    protected List<TaskRequestVo> getCurrentTasks(){
        return taskQueue.stream().map(x-> new TaskRequestVo(x.taskId, x.processingTime, x.awaitTaskCompletion)).toList();
    }

    private boolean isAvailable(){
        return currentCount < MAX_THREAD_COUNT;
    }
}
