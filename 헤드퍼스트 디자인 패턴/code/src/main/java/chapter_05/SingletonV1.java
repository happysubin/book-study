package chapter_05;

public class SingletonV1 {

    private static SingletonV1 uniqueInstance;

    private SingletonV1(){}

    public static SingletonV1 getInstance(){
        if(uniqueInstance == null){
            uniqueInstance = new SingletonV1();
        }
        return uniqueInstance;
    }
}
