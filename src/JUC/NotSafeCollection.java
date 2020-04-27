package JUC;/*
* 线程不安全的list set map
* java.util.ConcurrentModificationException
* new CopyOnWriteArrayList();
* new CopyOnWriteArraySet();
* new ConcurrentHashMap();
*  以及解决方案
* new ArrayList<>();  ===>  new Vector();  Collections.synchronizedList(new ArrayList<>());  new CopyOnWriteArrayList();
* new HashSet();   ===>  Collections.synchronizedSet(new HashSet<>());   new CopyOnWriteArraySet();
* new HashMap();   ===>  new ConcurrentHashMap();
* */
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public class NotSafeCollection {

    public static void main(String[] args) {
        notSafeLIst();
        notSafeSet();
        notSafeMap();
    }

    private static void notSafeMap() {
        Map map = new ConcurrentHashMap();
        for (int i = 0; i < 60; i++){
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString());
                System.out.println(map);
            }, Thread.currentThread().getName()).start();
        }
    }

    private static void notSafeSet() {
        Set set = new CopyOnWriteArraySet();
        for (int i = 0; i < 30; i++){
            new Thread(() -> {
                set.add(UUID.randomUUID().toString());
                System.out.println(set);
            }, Thread.currentThread().getName()).start();
        }
    }

    private static void notSafeLIst() {
        List list = new CopyOnWriteArrayList();

        for (int i = 0; i < 30; i++){
            new Thread(() -> {
                list.add(UUID.randomUUID().toString());
                System.out.println(list);
            }, Thread.currentThread().getName()).start();
        }
    }


}
