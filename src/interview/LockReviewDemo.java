package interview;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
* * 多线程之间按照顺序调用，A-B-C
 * 三个线程启动
 *
 * A打印5次 B打印10次 Ｃ打印１５次
 * 轮流１０
*
* */
public class LockReviewDemo {

    public static void main(String[] args) {
        PrintClass printClass = new PrintClass();

/*        new Thread(() ->{
            for (int i = 0; i<10; i++){
                try {
                    printClass.printA();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"A").start();

        new Thread(() ->{
            for (int i = 0; i<10; i++){
                try {
                    printClass.printB();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"B").start();


        new Thread(() ->{
            for (int i = 0; i<10; i++){
                try {
                    printClass.printC();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"C").start();*/

        Lock lock = new ReentrantLock();

        Condition A = lock.newCondition();
        Condition B = lock.newCondition();
        Condition C = lock.newCondition();

        new Thread(() ->{
            for (int i = 0; i<10; i++){
                    try {
                        printClass.printNum(lock,A, B, 6, 1, 2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        },"AAA").start();

        new Thread(() ->{
            for (int i = 0; i<10; i++) {
                try {
                    printClass.printNum(lock,B, C, 11, 2, 3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"BBB").start();

        new Thread(() ->{
            for (int i = 0; i<10; i++) {
                try {
                    printClass.printNum(lock,C, A, 16, 3, 1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"CCC").start();

    }
}

class PrintClass{
    Lock lock = new ReentrantLock();
    Condition A = lock.newCondition();
    Condition B = lock.newCondition();
    Condition C = lock.newCondition();
    private int num = 1;

    public void printA() throws InterruptedException {
        lock.lock();
        try{
            while (num != 1)
             A.await();
            for (int i = 1 ; i<6; i++)
                System.out.println(Thread.currentThread().getName()+"\tAAAAAAAA\t" +i);
            num = 2;
            B.signal();
        }finally{
            lock.unlock();
        }
    }

    public void printB() throws InterruptedException {
        lock.lock();
        try{
            while (num != 2)
                B.await();
            for (int i = 1 ; i<11; i++)
                System.out.println(Thread.currentThread().getName() +"\t BBBBBBBBB\t" + i);
            num = 3;
            C.signal();
        }finally{
            lock.unlock();
        }
    }

    public void printC() throws InterruptedException {
        lock.lock();
        try{
            while (num != 3)
                C.await();
            for (int i = 1 ; i<16; i++)
                System.out.println(Thread.currentThread().getName() +"\t CCCCCCCCC\t" + i);
            num = 1;
            A.signal();
        }finally{
            lock.unlock();
        }
    }

    public void printNum(Lock lock,Condition con1,Condition con2, int conunts, int flag, int flag2) throws InterruptedException {
        lock.lock();
        try{
            while (num != flag)
                con1.await();
            for (int i = 1; i<conunts; i++)
                System.out.println(Thread.currentThread().getName() +"\t"+i +"\t");
            num = flag2;
            con2.signal();
        }finally{
            lock.unlock();
        }

    }


}