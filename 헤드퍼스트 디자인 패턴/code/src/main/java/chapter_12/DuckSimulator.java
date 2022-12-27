package chapter_12;

public class DuckSimulator {

    public static void main(String[] args) {
        DuckSimulator simulator = new DuckSimulator();
        AbstractDuckFactory duckFactory = new CountingDuckFactory();
        simulator.simulate(duckFactory);
    }

    private void simulate(AbstractDuckFactory duckFactory) {

        Quackable redheadDuck = duckFactory.createRedheadDuck();
        Quackable duckCall = duckFactory.createDuckCall();
        Quackable rubberDuck = duckFactory.createRubberDuck();
        Quackable gooseAdapter = new GooseAdapter(new Goose());

        System.out.println("오리 시뮬레이션 게임 + 무리");

        Flock flockOfDucks = new Flock();

        flockOfDucks.add(redheadDuck);
        flockOfDucks.add(duckCall);
        flockOfDucks.add(rubberDuck);
        flockOfDucks.add(gooseAdapter);

        Flock flockOfMallards = new Flock();

        Quackable mallardDuck1 = duckFactory.createMallardDuck();
        Quackable mallardDuck2 = duckFactory.createMallardDuck();
        Quackable mallardDuck3 = duckFactory.createMallardDuck();
        Quackable mallardDuck4 = duckFactory.createMallardDuck();

        flockOfMallards.add(mallardDuck1);
        flockOfMallards.add(mallardDuck2);
        flockOfMallards.add(mallardDuck3);
        flockOfMallards.add(mallardDuck4);

        flockOfDucks.add(flockOfMallards);

        System.out.println("전체 무리");
        simulate(flockOfDucks);

        System.out.println("물오리 무리");
        simulate(flockOfMallards);

        System.out.println("오리 시뮬레이션 게임 + 옵저버");
        Quacklogist quacklogist = new Quacklogist();
        flockOfDucks.registerObserver(quacklogist);

        System.out.println("전체 무리");
        simulate(flockOfDucks);


        System.out.println("오리가 소리 낸 횟수 : " + QuackCounter.getNumberOfQuacks());
    }

    void simulate(Quackable duck){
        duck.quack();
    }
}
