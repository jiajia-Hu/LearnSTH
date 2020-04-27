package JUC;/*
* 三个售票员同时卖出30张票
* */


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SaleTicket {

    public static void main(String[] args) {
        Ticket ticket = new Ticket();

        new Thread(() -> {
            for (int i = 1; i <= 40; i++)
                ticket.sale();
        },"A").start();

        new Thread(() -> {
            for (int i = 1; i <= 40; i++)
                ticket.sale();
        },"B").start();

        new Thread(() -> {
            for (int i = 1; i <= 40; i++)
                ticket.sale();
        },"C").start();
    }
}

class Ticket{
    private int ticketNum = 30;

    Lock lock = new ReentrantLock();

    public void sale(){
        lock.lock();
        try {
            if(ticketNum > 0)
                System.out.println(Thread.currentThread().getName() +"卖出第" + ticketNum -- +"张票，还剩下" + ticketNum);
        }finally {
            lock.unlock();
        }

    }
}