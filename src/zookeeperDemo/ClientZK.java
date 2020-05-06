package zookeeperDemo;

public class ClientZK {

    public static void main(String[] args) {

        for (int i =0; i<10; i++){
            //多个jvm
            new Thread(() ->{
                new OrderNumsService().getOrderNum();
            },String.valueOf(i)).start();
        }

    }
}
