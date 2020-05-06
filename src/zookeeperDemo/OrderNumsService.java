package zookeeperDemo;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OrderNumsService {

    OrderNumUntil orderNumUntil = new OrderNumUntil();
//
     ZKLock zkLock = new ZKDistributedLock();

   public void getOrderNum(){
         zkLock.zklocks();
      try{
         System.out.println("---->orderService"+orderNumUntil.getX());
      }catch (Exception e){
         e.printStackTrace();
      }finally {
         zkLock.zkunlock();
      }
   }




}
