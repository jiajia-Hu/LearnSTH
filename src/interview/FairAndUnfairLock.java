package interview;

/*
* fair：队列 先来后到
*
* unfair：允许插队，能插队就插队
* ReentrantLock 默认unfair
*
* 公平锁
    是指多个线程按照申请锁的顺序来获取锁类似排队打饭 先来后到
非公平锁
    是指在多线程获取锁的顺序并不是按照申请锁的顺序,有可能后申请的线程比先申请的线程优先获取到锁
    * 是指在多线程获取锁的顺序并不是按照申请锁的顺序,有可能后申请的线程比先申请的线程优先获取到锁,在高并发的情况下,
    * 有可能造成优先级反转或者饥饿现象在高并发的情况下,有可能造成优先级反转或者饥饿现象
*
*
* */


public class FairAndUnfairLock {


}
