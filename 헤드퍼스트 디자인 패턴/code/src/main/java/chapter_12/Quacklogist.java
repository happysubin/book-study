package chapter_12;

public class Quacklogist implements Observer{
    @Override
    public void update(QuackObservable duck) {
        System.out.println("꽥꽥학자: " + duck + " 가 방금 소리냈다.");
    }
}
