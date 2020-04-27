package JUC;/*
* 1. 标准访问，email 和msg顺序: email msg
* 2. 在邮件暂停3秒钟，Email和msg顺序： Email  mag
* 3.新增普通sayHello  email 和SayHello 顺序: sayHello Email
* 4. 2个手机 email 和 mag 顺序： msg email
* 5. 2 个static 同步方法 同一个手机 email和msg顺序: email msg
* 6. 2 个static 同步方法 2个手机 email和msg顺序: email msg
* 6. 1 个static 同步方法, 1个普通同步方法， 同一个手机 email和msg顺序: msg email
* 6. 1 个static 同步方法, 1个普通同步方法， 2个手机 email和msg顺序: msg email
*
* 对于普通同步方法：锁是指当前对象
* 对于同步代码块： 锁是指{}里面内容
* 对于static同步方法：锁指class
* */


import java.util.concurrent.TimeUnit;

public class LocksDemo {

    public static void main(String[] args) throws Exception {
        Phone phone = new Phone();
        Phone phone2 = new Phone();

        new Thread(() -> {
            try {
                phone.sendEmail();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"A").start();

            Thread.sleep(400);


        new Thread(() -> {
            try {
                //phone.sendMsg();
               //phone.sayHello();
                phone2.sendMsg();
            } catch (Exception e) {
                e.printStackTrace();
            }
        },"B").start();

    }
}


class Phone{

    public static synchronized void sendEmail() throws Exception {
        TimeUnit.SECONDS.sleep(3);
        System.out.println("+++++++Email");
    }

    public synchronized void sendMsg() throws Exception{
        System.out.println("========Msg");
    }

    public void sayHello(){
        System.out.println("========Hello");
    }
}
