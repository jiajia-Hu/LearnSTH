package interview;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {

    public static void main(String[] args) {
        Phone phone = new Phone();

        new Thread(() ->{
            phone.send();
        },"A").start();


        new Thread(() ->{
            phone.receive();
        },"B").start();


        new Thread(() ->{
            phone.get();
        },"c").start();


        new Thread(() ->{
            phone.get();
        },"d").start();
    }


}



class Phone{

    public synchronized void send(){
        System.out.println(Thread.currentThread().getName()+"\t ==========sned()");
        receive();

    }

    public synchronized void receive(){
        System.out.println(Thread.currentThread().getName()+"\t ++++++++++=receive()");
        try {
            TimeUnit.SECONDS.sleep(6);
        } catch (InterruptedException e) {

        }
    }


    Lock lock = new ReentrantLock();

    public void get(){
        lock.lock();
        try{
            System.out.println(Thread.currentThread().getName()+"\t ~~~~~~~~~~~~~~~~~~~~~~~~~");
            set();
        }finally{
            lock.unlock();
        }
    }

    public void set(){
        lock.lock();
        try{
            System.out.println(Thread.currentThread().getName()+"\t ************************");

        }finally{
            lock.unlock();
        }
    }

}