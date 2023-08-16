package org.example;


import org.example.executor.AsyncTask;
import org.example.executor.TaskExecutor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

@SpringBootApplication
public class App
{
    public static void main( String[] args ){
        SpringApplication.run(App.class, args);
    }

    public static void doTheThing(){
        TaskExecutor executer = new TaskExecutor();
        AsyncTask taskA = new AsyncTask(executer, 1l, Boolean.TRUE, "123");
        AsyncTask taskB = new AsyncTask(executer, 2l, Boolean.FALSE, "123");
        AsyncTask taskC = new AsyncTask(executer, 3l, Boolean.TRUE, "123");
        AsyncTask taskD = new AsyncTask(executer, 4l, Boolean.FALSE, "123");
        AsyncTask taskE = new AsyncTask(executer, 5l, Boolean.TRUE, "123");
        System.out.println("===TRIGGERING TASKS===");
        executer.executeTasks(Arrays.asList(taskA, taskB, taskC, taskD, taskE), new CountDownLatch(5));
        System.out.println("===DONE TRIGGERING===");
    }
}
