package JUC;/*
* 题目： 两个线程操作初始值是0的变量
* 一个线程加一，一个线程减一
* 实现交替10轮 最后变量指是0
*
* 1. 高聚合低内聚 线程 操作 资源类
* 2. 判断 、 干活 、通知
* 3. 防止多线程的虚假唤醒， 有wiait就用while
*
* 多线程套路 +　while判断　＋　新写法
*
*
* */


public class ProdConsumerDemo {

        public static void main(String[] args) throws InterruptedException {
            Product product = new Product();

            new Thread(() -> {
                for(int i = 0; i < 10; i++){
                try {
                    product.addOne();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                    }
            }, "A").start();

            new Thread(() -> {
               for(int i = 0; i < 10; i++){
                try {
                    product.subOne();
                } catch (Exception e) {
                    e.printStackTrace();
                    }
                }
             }, "B").start();

            new Thread(() -> {
                for(int i = 0; i < 10; i++){
                    try {
                        product.addOne();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, "C").start();

            new Thread(() -> {
                for(int i = 0; i < 10; i++){
                    try {
                        product.subOne();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, "D").start();
        }

}


class Product{

    private int num = 0;

    public synchronized void addOne() throws Exception{
        //1. 判断
        while(num != 0) {
            //如果用if 线程卡在这 等在醒过来就没判断， while会被重新判断
            this.wait();
        }
        //2. 干活
        num++;
        System.out.println(Thread.currentThread().getName() + "\t" + num+"+++++++");
        //3. 通知
        this.notifyAll();
    }

    public synchronized void subOne() throws Exception{
        while(num == 0){
            this.wait();
        }
        num--;
        System.out.println(Thread.currentThread().getName() + "\t" + num+"-------");
        this.notifyAll();
    }
}