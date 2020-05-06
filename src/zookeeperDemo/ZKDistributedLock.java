package zookeeperDemo;

import com.github.zkclient.IZkDataListener;

import java.util.concurrent.CountDownLatch;

public class ZKDistributedLock extends ZKAbstractTemplate{


    @Override
    public boolean tryZKLock() {
        try {
            zkClient.createEphemeral(path);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public void waitZKLock() {

        IZkDataListener iZkDataListener = new IZkDataListener() {
            @Override
            public void handleDataChange(String s, byte[] bytes) throws Exception {

            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                if (countDownLatch != null)
                    countDownLatch.countDown();
            }
        };
        zkClient.subscribeDataChanges(path, iZkDataListener);

        //存在这个节点说明 有人已经建立了这个节点  就得等着  不能往下走
        if(zkClient.exists(path)){
            countDownLatch = new CountDownLatch(1);
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        zkClient.unsubscribeDataChanges(path,iZkDataListener);
    }
}
