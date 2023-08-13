package org.example;


import java.util.concurrent.CountDownLatch;

public class AsyncTask extends Thread {
    public TaskExecuter executer;
    public Boolean awaitTaskCompletion;
    public String suffix;

    public CountDownLatch latch;

    AsyncTask(TaskExecuter executer, String suffix, Boolean awaitTaskCompletion){
        this.executer = executer;
        this.suffix = suffix;
        this.awaitTaskCompletion = awaitTaskCompletion;
    }

    public void run() {
        try {
            System.out.println(">>> "+System.currentTimeMillis()+" "+suffix+" Initiated task");
            if(this.awaitTaskCompletion){
                this.sleep(5000);
                doCallBack();

            }
            else{
                doCallBack();
                this.sleep(5000);
            }
            System.out.println(">>> "+System.currentTimeMillis()+" "+suffix+" Done executing");
            this.executer.getTaskMonitor().release();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    private void doCallBack(){
        this.latch.countDown();
        System.out.println(">>> "+System.currentTimeMillis()+" "+suffix+" Callback called");
    }
}
