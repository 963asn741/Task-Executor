package org.example.executor;

import lombok.extern.slf4j.Slf4j;
import org.example.vo.TaskTRequestVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

@Component("customTaskExecutor")
@Slf4j
public class TaskExecutor {

   @Autowired
   protected TaskMonitor taskMonitor;

   public void executeTasks(List<AsyncTask> tasks, CountDownLatch latch, String requestId){
      log.info("EXEC -> TaskExecutor -> executeTasks()");

      for (AsyncTask task : tasks) {
         task.latch = latch;
         startTask(task, requestId);
      }
      try {
         latch.await();
      } catch (InterruptedException e) {
         throw new RuntimeException(e);
      }
      log.info("successfully finished processing tasks with reqId "+requestId);
   }

   public List<TaskTRequestVo> getRunningTasks(){
      return taskMonitor.getCurrentTasks();
   }

   private void startTask(AsyncTask task, String requestId ){
      taskMonitor.acquire(task);
      task.start();
   }
}
