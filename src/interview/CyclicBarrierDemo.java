package interview;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {
    public static void main(String[] args) {
        //第一个参数是等待多少个线程就可以了，第二个要执行的线程
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7,() -> {
           System.out.println("已经集齐了所有龙珠 召唤神龙");
        });

        for (int i = 1;i <8;i++){
            final int temp = i;
            new Thread(() ->{
                System.out.println(Thread.currentThread().getName()+"\t集齐了第" + temp +"号龙珠");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }
    }
}
