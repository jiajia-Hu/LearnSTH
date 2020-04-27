package interview;

import java.util.concurrent.*;

public class MyThreadPoolDemo {
    public static void main(String[] args) {
//        executorService();

        ExecutorService executorService = new ThreadPoolExecutor(2,
                5,
                2L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(5),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy());
        try{
            for (int i = 1; i < 20; i++){
                final int temp = i;
                executorService.execute(()->{
                    System.out.println(Thread.currentThread().getName() +"\t打印数值" + temp);
                    try {
                        TimeUnit.MILLISECONDS.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            executorService.shutdown();
        }


    }

    private static void executorService() {
        //一池5个处理线程
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        //一池1线程
        ExecutorService executorService1 = Executors.newSingleThreadExecutor();
        //一池多线程
        ExecutorService executorService2 = Executors.newCachedThreadPool();
        try{
            //10个用户线程 5个处理线程
            for (int i = 1;i < 11; i++){
                final int temp = i;
                executorService2.execute(()->{
                    System.out.println(Thread.currentThread().getName() +"\t" + temp);
                });
            }
        }catch (Exception e){

        }finally {
            executorService.shutdown();
        }
    }
}
