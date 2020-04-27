package JVM;

public class ClassLoaderDemo {

    public static void main(String[] args) {
        Object object = new Object();
        System.out.println(object.getClass().getClassLoader());


        ClassLoaderDemo classLoaderDemo = new ClassLoaderDemo();
        System.out.println(classLoaderDemo.getClass().getClassLoader());
        System.out.println(classLoaderDemo.getClass().getClassLoader().getParent());
        System.out.println(classLoaderDemo.getClass().getClassLoader().getParent().getParent());


    }
}
