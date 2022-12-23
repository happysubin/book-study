package chapter_5;

public class SingletonV4 {

    private volatile static SingletonV4 uniqueInstance;

    private SingletonV4(){}

    public static synchronized SingletonV4 getInstance(){
        if(uniqueInstance == null){
            synchronized (SingletonV4.class){
                if(uniqueInstance == null){
                    uniqueInstance = new SingletonV4();
                }
            }
        }
        return uniqueInstance;
    }
}
