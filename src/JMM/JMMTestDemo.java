package JMM;


import java.util.concurrent.TimeUnit;

/*
* JMM java 内存模型
* 可见性  原子性  有序性
*
* 可见性：
* int num = 0, 没有volatile修饰,没有可见性
*
* */
public class JMMTestDemo {
    public static void main(String[] args) {
        Member member = new Member();
        seeOK(member);

        //addP(member);
    }

    private static void addP(Member member) {
        for(int i = 0; i < 20; i++){
            new Thread(() ->{
                for (int j = 0; j <1000; j++){
                    member.addPlus();
                }
            },"i").start();
        }

        while (Thread.activeCount() >2){
            Thread.yield();
        }

        System.out.println(member.num);
    }

    private static void seeOK(Member member) {

        System.out.println("*****************");
        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"\t");
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            member.addTo222();
            System.out.println(Thread.currentThread().getName()+"------update-------"  + member.num);
        },"AAAAAAAAA").start();

        //第二个main线程
        while (member.num == 0){
            //main不会知道值已经被改变 over 不会被打印
            //如果有volatile main就可见num
        }

        System.out.println("over");
    }




}


class Member{
     volatile int num = 0;

    public void addTo222(){
        this.num = 222;
    }


    public void addPlus(){
        num++;
    }
}