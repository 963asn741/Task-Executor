package org.example;

import lombok.Data;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

@Data
public class TaskExecuter {
   private Semaphore taskMonitor;

   public void executeTasks(List<AsyncTask> tasks, CountDownLatch latch){
      init();
      for (AsyncTask task : tasks) {
         task.latch = latch;
         startTask(task);
      }
      try {
         latch.await();
      } catch (InterruptedException e) {
         throw new RuntimeException(e);
      }
   }

   private void init(){
      if (taskMonitor == null){
         taskMonitor = new Semaphore(3);
      }
   }

   private void startTask(AsyncTask task){
      try {
         taskMonitor.acquire();
         task.start();
      } catch (InterruptedException e) {
         throw new RuntimeException(e);
      }
   }
}
