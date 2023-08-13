package org.example;


import java.util.Arrays;
import java.util.concurrent.CountDownLatch;


public class App
{
    public static void main( String[] args )
    {
        doTheThing();
    }

    public static void doTheThing(){
        TaskExecuter executer = new TaskExecuter();
        AsyncTask taskA = new AsyncTask(executer, "A", Boolean.TRUE);
        AsyncTask taskB = new AsyncTask(executer, "B", Boolean.FALSE);
        AsyncTask taskC = new AsyncTask(executer, "C", Boolean.TRUE);
        AsyncTask taskD = new AsyncTask(executer, "D", Boolean.FALSE);
        AsyncTask taskE = new AsyncTask(executer, "E", Boolean.TRUE);
        System.out.println("===TRIGGERING TASKS===");
        executer.executeTasks(Arrays.asList(taskA, taskB, taskC, taskD, taskE), new CountDownLatch(5));
        System.out.println("===DONE TRIGGERING===");
    }
}
