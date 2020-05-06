package interview;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ProdCus_NewVersion {

    public static void main(String[] args) {
        Cake cake = new Cake(new ArrayBlockingQueue<String>(6));

        new Thread(() ->{

            try {
                cake.prod();
                System.out.println();
                System.out.println();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"AAA").start();


        new Thread(() ->{
            try {
                cake.consumer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"BBB").start();


        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main 停止线程生产");
        cake.stop();
    }
}


class Cake{

    private volatile static boolean FLAG = true;

    BlockingQueue<String> blockingQueue = null;

    AtomicInteger atomicInteger = new AtomicInteger(0);

    public Cake(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }
    CountDownLatch countDownLatch = new CountDownLatch(1);

    public void prod() throws InterruptedException {
        String data ;
        boolean offer;
        while (FLAG){
            data = atomicInteger.incrementAndGet()+"";
            offer = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
            if(offer){
                System.out.println(Thread.currentThread().getName()+"\t 生产一个数据成功"+data);
            }else {
                System.out.println(Thread.currentThread().getName()+"\t 生产数据失败");
            }
            TimeUnit.MILLISECONDS.sleep(10);
        }
        System.out.println("FLAG 是false 不生产了");
    }

    public void consumer() throws InterruptedException {
        while (FLAG){
            String result = blockingQueue.poll(2L, TimeUnit.SECONDS);
            if(null == result || "".equals(result)){
                FLAG = false;
                System.out.println(Thread.currentThread().getName()+"\t 等了2秒， 没有数据消费");
                return;
            }
            System.out.println(Thread.currentThread().getName()+"\t 消费数据成功"+result);
        }
    }

    public void  stop(){
        FLAG = false;
    }




}
