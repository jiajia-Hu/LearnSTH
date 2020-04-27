package interview;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class ABADemo {

    private static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);

    private static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100,1);

    public static void main(String[] args) {
        System.out.println("==============产生ABA的例子================");

        new Thread(() ->{
            System.out.println(atomicReference.compareAndSet(100,120) + "\t A第一次换成B\t" + atomicReference.get() );
            System.out.println(atomicReference.compareAndSet(120,100) + "\t B第一次换成A\t" + atomicReference.get() );
        },"t1").start();


        new Thread(() ->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicReference.compareAndSet(100,300)+"\t 线程2在不知情的情况下 把最原始的A换成自己的结果" + atomicReference.get());

        },"t2").start();

        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("===============ABA问题的解决================");
        new Thread(() ->{
            System.out.println("当前的stamp的值\t"+atomicStampedReference.getStamp());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+ "\t"+atomicStampedReference.compareAndSet(100,110,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1));
            System.out.println("第一次版本号\t" + atomicStampedReference.getStamp() +"\t值" + atomicStampedReference.getReference());
            System.out.println(Thread.currentThread().getName()+ "\t"+atomicStampedReference.compareAndSet(110,100,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1));
            System.out.println("第二次版本号\t" + atomicStampedReference.getStamp() +"\t值" + atomicStampedReference.getReference());

        },"t3").start();

        new Thread(() ->{
            int stamp = atomicStampedReference.getStamp();
            System.out.println("当前的stamp的值\t"+stamp);
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+ "\t"+atomicStampedReference.compareAndSet(100,300,stamp,atomicStampedReference.getStamp()+1));
            System.out.println("当前版本号\t" + atomicStampedReference.getStamp() +"\t最终值\t" + atomicStampedReference.getReference());
        },"t4").start();
    }
}
