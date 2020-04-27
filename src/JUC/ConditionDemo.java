package JUC;/*
* 多线程之间按照顺序调用，A-B-C
* 三个线程启动
*
* A打印5次 B打印10次 Ｃ打印１５次
* 轮流１０
*
* */

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionDemo {


    public static void main(String[] args) {
        Print print = new Print();

            new Thread(() -> {
                try {
                    for (int i = 0; i<10; i++) {
                        print.aPrint2();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }, "1").start();


            new Thread(() -> {
                try {
                    for (int i = 0; i<10; i++) {
                        print.bPrint2();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }, "2").start();

        new Thread(() -> {
            try {
                for (int i = 0; i<10; i++) {
                     print.cPrint2();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }, "3").start();
    }

}


class Print{
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    Condition aCon = lock.newCondition();
    Condition bCon = lock.newCondition();
    Condition cCon = lock.newCondition();

    boolean A = false;
    boolean B = false;
    boolean C = false;

    private int num = 1;


    public void aPrint() throws InterruptedException {
        lock.lock();
        try {
            while (A)
                condition.await();
            for (int i = 0; i<5; i++)
                System.out.println(Thread.currentThread().getName() + "\t"+i);
            A = true;
            B = false;
            C = true;
            condition.signalAll();
        }finally {
            lock.unlock();
        }
    }

    public void bPrint() throws InterruptedException {
        lock.lock();
        try {
            while (B)
                condition.await();
            for (int i = 0; i<10; i++)
                System.out.println(Thread.currentThread().getName() + "\t"+i);
            A = true;
            B = true;
            C = false;
            condition.signalAll();
        }finally {
            lock.unlock();
        }
    }

    public void cPrint() throws InterruptedException {
        lock.lock();
        try {
            while (C)
                condition.await();
            for (int i = 0; i<15; i++)
                System.out.println(Thread.currentThread().getName() + "\t"+i);
            A = false;
            B = true;
            C = true;
            condition.signalAll();
        }finally {
            lock.unlock();
        }
    }

    public void aPrint2() throws InterruptedException {
        lock.lock();
        try{
            while(num != 1)
                aCon.await();
            printNum(5);
            num = 2;
            bCon.signal();
        }finally{
            lock.unlock();
        }
    }

    public void bPrint2() throws InterruptedException {
        lock.lock();
        try{
            while(num != 2)
                bCon.await();
            printNum(10);
            num = 3;
            cCon.signal();
        }finally{
            lock.unlock();
        }
    }

    public void cPrint2() throws InterruptedException {
        lock.lock();
        try{
            while(num != 3)
                cCon.await();
            printNum(15);
            num  = 1;
            aCon.signal();
        }finally{
            lock.unlock();
        }
    }


    public void printNum(int x){
        for (int i = 1; i <= x; i++)
            System.out.println(Thread.currentThread().getName() + "\t"+i);
    }

}