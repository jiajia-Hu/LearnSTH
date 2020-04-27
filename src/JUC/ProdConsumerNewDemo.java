package JUC;/*
* 新写法
* Synchronized  ===》 lock
* wait ===》  Condition.await
* notifyAll  ===>  Condition.signalAll
*
* */


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProdConsumerNewDemo {

    public static void main(String[] args) {
        Products products = new Products();

        for (int i = 0; i < 10; i++){
            new Thread(() -> {
                try {
                    products.addOne();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            },"A").start();
        }

        for (int j = 0; j < 10; j++){
            new Thread(() -> {
                try {
                    products.subOne();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            },"B").start();
        }

    }



}

class Products{
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    private int num = 0;

    public void addOne() throws Exception{
        lock.lock();
        try {
            //1. 判断
            while(num != 0) {
                //如果用if 线程卡在这 等在醒过来就没判断， while会被重新判断
                condition.await();
            }
            //2. 干活
            num++;
            System.out.println(Thread.currentThread().getName() + "\t" + num+"+++++++");
            //3. 通知
            condition.signalAll();
        }finally {
            lock.unlock();
        }

    }

    public void subOne() throws Exception{
        lock.lock();
        try{
            while (num == 0)
                condition.await();
            num--;
            System.out.println(Thread.currentThread().getName() + "\t" + num+"-------");
            condition.signalAll();
        }finally {
            lock.unlock();
        }
    }
}
