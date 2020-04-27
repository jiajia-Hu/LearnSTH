package summary.volatilesum;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/*
* volatile：
* 1.保证可见性
* 2.不保证原子性
* 3.禁止指令重排:
* int i = 1;
* int j = 2;
* i = i + j;
* 编译器会自动进行指令重排的优化 i 和j的数序可能会变化  volatile的作用就是禁止指令重排
* 按照顺序执行
*
* JMM java 内存模型满足：可见性 原子性 有序性
*
* */
public class VolatileDemo {

    public static void main(String[] args) {
        Number number = new Number();
        volatileSeeOk(number);
        noVolatileNotSeeOk(number);
        noAtomic(number);
        atomicOk(number);

    }

    private static void atomicOk(Number number) {
        /*
        * atomicInteger 可以解决原子性
        * */
        for (int i = 0; i < 10; i++){
            new Thread(() ->{
                for (int j = 0; j < 2000; j++){
                    number.addPlus();
                }
            },String.valueOf(i)).start();
        }
        /*保证全部结束 没有线程在运行
         * main 和GC */
        while (Thread.activeCount() > 2){
            Thread.yield();
        }
        System.out.println("最后num的结果\t" + number.atomicInteger.get());
    }

    private static void noAtomic(Number number) {
        /*
        * 原子性:全部执行一致
        * 开启10个线程，没个线程执行i++ 2000次
        * 原子性结果 20000 但是volatile不保证20000
        * 结果小于20000 出现了数据丢失
        * 会出现某些时刻 线程1像主内村写数据的时候被挂起
        * 线程2 写进去了 然后线程1也写进去了 覆盖了线程2 的数据
        * 导致少了一次i++  造成丢数据情况
        * */
        for (int i = 0; i < 10; i++){
            new Thread(() ->{
                for (int j = 0; j < 2000; j++){
                    number.addPlusPlus();
                }
            },String.valueOf(i)).start();
        }
        /*保证全部结束 没有线程在运行
        * main 和GC */
        while (Thread.activeCount() > 2){
            Thread.yield();
        }
        System.out.println("最后num的结果\t" + number.volatileNum);
    }

    private static void volatileSeeOk(Number number) {
        //noVolatileNotSeeOk(number);
        //可见性案例：volatileNum，
        // 两个线程 线程A 和main线程， 保证A线程先执行addNumTo2020
        //检测volatileNum值的变化能够被main线程可见
        //将会打印出结束语
        new Thread(() -> {
            /*先让main线程获取num =0 睡一下*/
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            number.addNumTo202();
            System.out.println("update num to 2020");
        }, "A").start();
        while (number.volatileNum == 0) {

        }
        System.out.println("see ok over======");
    }

    private static void noVolatileNotSeeOk(Number number) {
        //可见性案例：当前num没有volatile关键字修饰，
        // 两个线程 线程A 和main线程， 保证A线程先执行addNumTo2020
        //检测num值的变化不能被main线程可见
        new Thread(() ->{
            /*先让main线程获取num =0 睡一下*/
            try {
                TimeUnit.MILLISECONDS.sleep(200);} catch (InterruptedException e) {
                e.printStackTrace();
            }
            number.addNumTo202();
            System.out.println("update num to 2020");
        },"A").start();
        while (number.num == 0){

        }
        System.out.println("never system out anything");
    }

}


class Number{
    public int num = 0;

    public volatile int volatileNum = 0;

    AtomicInteger atomicInteger = new AtomicInteger(0);

    public void addNumTo202(){
        this.num = 2020;
        this.volatileNum = 2020;
    }

    public void addPlusPlus(){
        volatileNum ++;
    }

    public void addPlus(){
        //相当于i++
        atomicInteger.getAndIncrement();
    }
}