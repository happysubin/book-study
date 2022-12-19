package chapter_1;

public class App {
    public static void main(String[] args) {
        QuackBehavior quack = new Quack();
        FlyBehavior flyBehavior = new FlyWithWings();
        Duck duck = new MallardDuck(flyBehavior, quack);
        duck.performQuack();
        duck.performFly();
    }
}
