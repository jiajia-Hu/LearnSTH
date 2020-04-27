package interview;

public class DeadLockDemo {

    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";

        new Thread(new DeadLock(lockA,lockB),"AAA").start();
        new Thread(new DeadLock(lockB,lockA),"BBB").start();
    }

}


class DeadLock implements Runnable{

    private String lockA;

    private String lockB;

    public DeadLock(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA){
            System.out.println(Thread.currentThread().getName()+"\t"+lockA);
            System.out.println();
            synchronized (lockB){
                System.out.println(Thread.currentThread().getName()+"\t"+lockB);
            }
        }
    }
}
