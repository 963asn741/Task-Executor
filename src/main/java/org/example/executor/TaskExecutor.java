package org.example.executor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

@Component("customTaskExecutor")
@Slf4j
public class TaskExecutor {
   protected Semaphore taskMonitor;

   public void executeTasks(List<AsyncTask> tasks, CountDownLatch latch, Integer maxThreadCount){
      log.info("EXEC -> TaskExecutor -> executeTasks()");
      init(maxThreadCount);
      for (AsyncTask task : tasks) {
         task.latch = latch;
         startTask(task);
      }
      try {
         latch.await();
      } catch (InterruptedException e) {
         throw new RuntimeException(e);
      }
      log.info("successfully finished processing tasks");
   }

   private void init(Integer maxThreadCount){
      if (taskMonitor == null){
         taskMonitor = new Semaphore(maxThreadCount);
      }
   }

   private void startTask(AsyncTask task){
      try {
         taskMonitor.acquire();
         log.info(">>> Trigerring task "+task.taskId);
         task.start();
      } catch (InterruptedException e) {
         throw new RuntimeException(e);
      }
   }
}
