package interview;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreDemo {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(4);//模拟4个车位
        for (int i = 1; i< 7;i++){//6辆车子
            final int temp = i;
            new Thread(() ->{
                try {
                    semaphore.acquire();
                    System.out.println("当前第" + Thread.currentThread().getName() +"辆车抢到车位");
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println(Thread.currentThread().getName() +"离开");
                    semaphore.release();
                }
            },String.valueOf(i)).start();

        }

    }
}
