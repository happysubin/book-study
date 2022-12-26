package chapter_07.adapter;

public class TurkeyAdapter implements Duck { //적응시킬 형식의 인터페이스를 구현

    private Turkey turkey;

    public TurkeyAdapter(Turkey turkey) { //기존 형식 객체의 레퍼런스가 필요하다.
        this.turkey = turkey;
    }

    @Override
    public void quack() { //인터페이스에 들어있는 메서드를 모두 구현한다.
        turkey.gobble();
    }

    @Override
    public void fly() {
        for (int i = 0; i < 5; i++) {
            turkey.fly();
        }
    }
}
