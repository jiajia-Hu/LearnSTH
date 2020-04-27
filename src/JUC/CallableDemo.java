package JUC;/*
* FutureTask()
* 同时支持Callable 和 Runnable
* */
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CallableDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask =  new FutureTask(new MyThread2());
        new Thread(futureTask,"A").start();
        Integer result = futureTask.get();
        System.out.println(result);
    }

}

class MyThread implements Runnable{

    @Override
    public void run() {

    }
}

class MyThread2 implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {

        return 1024;
    }
}