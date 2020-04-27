package interview;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 0; i<6;i++){
            new Thread(() ->{
                System.out.println(Thread.currentThread().getName()+"\t号线程");
                countDownLatch.countDown();
            },CountDownLatchEnum.getVal(i).getVal()).start();
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+"\t最终线程");
    }

}
