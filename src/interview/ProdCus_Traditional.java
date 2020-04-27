package interview;

/*
题目： 一个初始值为0的变量，两个线程交替操作 一个+1 一个-1 来5轮
* */
public class ProdCus_Traditional {
    public static void main(String[] args) {
        DoWithNum doWithNum = new DoWithNum();

        new Thread(() ->{
            for (int i =0; i<6; i++){
                try {
                    doWithNum.add();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"AA").start();

        new Thread(() ->{
            for (int i = 0; i<6; i++){
                try {
                    doWithNum.sub();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"BB").start();
    }
}

class DoWithNum{
    private int num = 0;

    public synchronized void add() throws InterruptedException {
        while (num != 0)
            this.wait();
        num++;
        System.out.println(Thread.currentThread().getName() +"\t 加一" + num);
        notifyAll();
    }

    public synchronized void sub()throws InterruptedException{
        while (num == 0)
            this.wait();
        num--;
        System.out.println(Thread.currentThread().getName() +"\t 减一" + num);
        notifyAll();
    }

}