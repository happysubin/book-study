package chapter_5;

public class SingletonV2 {

    private static SingletonV2 uniqueInstance;

    private SingletonV2(){}

    public static synchronized SingletonV2 getInstance(){
        if(uniqueInstance == null){
            uniqueInstance = new SingletonV2();
        }
        return uniqueInstance;
    }
}
