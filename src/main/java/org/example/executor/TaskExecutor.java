package org.example.executor;

import lombok.extern.slf4j.Slf4j;
import org.example.vo.TaskRequestVo;
import org.example.vo.TaskResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

@Component("customTaskExecutor")
@Slf4j
public class TaskExecutor {

   @Autowired
   protected TaskMonitor taskMonitor;

   @Autowired
   private TaskHandler taskHandler;

   private ConcurrentLinkedQueue<AsyncTask> taskQueue = new ConcurrentLinkedQueue<>();
   private AtomicBoolean executorIsRunning = new AtomicBoolean();
   private ConcurrentHashMap<String, List<TaskResponseVo>> requestTracker = new ConcurrentHashMap();


   public List<TaskResponseVo> executeTasks(List<AsyncTask> tasks, CountDownLatch latch){
      log.info("EXEC -> TaskExecutor -> executeTasks()");
      for (AsyncTask task : tasks) {
         task.latch = latch;
      }
      taskQueue.addAll(tasks);
      List<TaskResponseVo> response = new ArrayList<>();
      if(!tasks.isEmpty()){
         requestTracker.put(tasks.get(0).reqId, response);
         Runnable executable = this::triggerExecutor;
         new Thread(executable).start();
         try {
            latch.await();
         } catch (InterruptedException e) {
            throw new RuntimeException(e);
         }
         log.info("successfully finished triggering tasks");
         System.gc();
         return requestTracker.get(tasks.get(0).reqId);
      }
      return Collections.emptyList();
   }

   public List<TaskRequestVo> getRunningTasks(){
      return taskMonitor.getCurrentTasks();
   }

   protected void callBack(AsyncTask task){
      this.requestTracker.get(task.reqId).add(new TaskResponseVo(task.taskId, task.processingTime, task.taskStatus));
   }

   private synchronized void triggerExecutor(){
      if(!executorIsRunning.get()){
         if (!taskQueue.isEmpty()) executorIsRunning.set(true);
         while(!taskQueue.isEmpty()){
            startTask(taskQueue.poll());
         }
         executorIsRunning.set(false);
      }
   }

   private void startTask(AsyncTask task){
      taskMonitor.acquire(task);
      //log.info("acquired monitor lock");
      taskHandler.handleTaskExecution(task);
   }
}
