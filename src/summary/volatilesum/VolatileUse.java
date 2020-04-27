package summary.volatilesum;


/*
* 单例模式 DCL（double check lock）在锁的前面和后面都要验证check
*禁止指令重排
* */
public class VolatileUse {
    private static volatile VolatileUse volatileUse = null;

    private VolatileUse (){}

    public VolatileUse getInstence(){
        if (volatileUse == null){
            synchronized (VolatileUse.class){
                if(volatileUse == null)
                    volatileUse = new VolatileUse();
            }
        }
        return volatileUse;
    }
}
