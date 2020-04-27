package JUC;

public class ThreadDemo {
    public static void main(String[] args) {
        Cake cake = new Cake();

        new Thread(() -> {
            for (int i = 0; i<10; i++){
                try {
                    cake.add();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        },"A").start();

        new Thread(() -> {
            for (int i = 0; i<10; i++){
                try {
                    cake.sub();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        },"B").start();

        new Thread(() -> {
            for (int i = 0; i<10; i++){
                try {
                    cake.add();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        },"C").start();

        new Thread(() -> {
            for (int i = 0; i<10; i++){
                try {
                    cake.sub();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        },"D").start();
    }


}

class Cake{

    private int num = 0;

    public synchronized void add() throws InterruptedException {
        while(num != 0)
            this.wait();
        num++;
        System.out.println(Thread.currentThread().getName()+"\t"+num+"+++++++++++++++");
        this.notifyAll();
    }


    public synchronized void sub() throws InterruptedException {
        while (num == 0)
            this.wait();
        num--;
        System.out.println(Thread.currentThread().getName()+"\t"+num+"----------------");
        this.notifyAll();
    }
}
