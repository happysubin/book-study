package chapter_05;

public class SingletonV3 {

    private static SingletonV3 uniqueInstance = new SingletonV3();

    private SingletonV3(){}

    public static synchronized SingletonV3 getInstance(){
        return uniqueInstance;
    }
}
