package interview;


import lombok.Getter;

import java.util.concurrent.CountDownLatch;

public enum CountDownLatchEnum {
    One(1,"我"),
    TWO(2,"想"),
    THREE(3,"吃"),
    FOUR(4,"炸"),
    FIVE(5,"鸡"),
    SIX(0,"啊");


    @Getter
    private int id;
    @Getter
    private String val;
//    @Gettering val2;
//    private Strin
//    private Strg val2;

    CountDownLatchEnum(int id, String val){
        this.id = id;
        this.val = val;
    }

    public static CountDownLatchEnum getVal(int index){
        CountDownLatchEnum[] values = CountDownLatchEnum.values();
        for (CountDownLatchEnum val: values) {
            if(index == val.getId()){
                return val;
            }
        }
        return null;
    }

}
