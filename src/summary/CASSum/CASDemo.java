package summary.CASSum;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/*
*
*CAS compare and swap
*
* */
public class CASDemo {
    public static void main(String[] args) {
        AtomicReference<Integer> atomicReference = new AtomicReference<>(6);

        /*查看unsafe类里面的compareAndSwapObject
        * */
  /*      public final boolean compareAndSet(V expect, V update) {
            return unsafe.compareAndSwapObject(this, valueOffset, expect, update);
        }*/

//        public final Object getAndSetObject(Object var1, long var2, Object var4) {
//            Object var5;
//            do {
//                var5 = this.getObjectVolatile(var1, var2);
//            } while(!this.compareAndSwapObject(var1, var2, var5, var4));
//
//            return var5;
//        }
        System.out.println(atomicReference.compareAndSet(6,100) +"\t" +atomicReference.get());
        System.out.println(atomicReference.compareAndSet(100,8) +"\t" + atomicReference.get());

    }
}
