package zookeeperDemo;

public class OrderNumUntil {

    private  static int x = 1;

    public String getX(){
        return "\t编号-----"+ (++x);
    }

}
