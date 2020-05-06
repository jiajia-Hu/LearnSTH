package zookeeperDemo;


import com.github.zkclient.ZkClient;

import java.util.concurrent.CountDownLatch;

public abstract class ZKAbstractTemplate implements ZKLock{

    private static final String zkServers ="39.100.197.19:2181";
    private static final int connectionTimeout = 50000;

    protected  String path = "/node1";

    protected CountDownLatch countDownLatch = null;

    ZkClient zkClient = new ZkClient(zkServers,connectionTimeout);


    @Override
    public void zklocks() {
        if(tryZKLock()){
            System.out.println(Thread.currentThread().getName()+"\t获取锁成功");
        }else {
            waitZKLock();
            zklocks();
        }

    }

    public abstract boolean tryZKLock();

    public abstract void waitZKLock();

    @Override
    public void zkunlock() {
        if(zkClient != null) {
            zkClient.close();
        }
        System.out.println(Thread.currentThread().getName()+"\t释放锁");
        System.out.println();
        System.out.println();
    }
}
