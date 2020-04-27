package interview;

import com.sun.org.apache.xpath.internal.objects.XObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLock {
    public static void main(String[] args) {
        MyCache myCache = new MyCache();

        for (int i = 1; i<6; i++){
            final int temp = i;
            new Thread(() ->{
                myCache.write(temp,temp+"");
            },String.valueOf(i)).start();
        }

        for (int i = 1; i<6; i++){
            final int temp = i;
            new Thread(() ->{
                myCache.read(temp);
            },String.valueOf(i)).start();
        }
    }
}


class MyCache{

    private volatile Map<Integer, Object> map = new HashMap<>();

    Lock lock = new ReentrantLock();
    ReentrantReadWriteLock reLock = new ReentrantReadWriteLock();

    public void write(Integer key, Object val){
//        lock.lock();
//        try{
        reLock.writeLock().lock();
        try{

            System.out.println(Thread.currentThread().getName()+"\t 正在写入++++" + key);
            map.put(key,val);
            System.out.println(Thread.currentThread().getName()+"\t 写入完成");
        }finally{
            reLock.writeLock().unlock();
        }

//        }finally{
//            lock.unlock();
//        }

    }

    public void read(Integer key){
//        lock.lock();
//        try{

        reLock.readLock().lock();
        try{
            System.out.println(Thread.currentThread().getName()+"\t 正在读取----");
            Object o = map.get(key);
            System.out.println(Thread.currentThread().getName()+"\t 读取完成\t"  +o);

        }finally{
            reLock.readLock().unlock();
        }

//        }finally{
//            lock.unlock();
//        }

    }
}