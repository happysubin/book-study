package chapter_03;

public class DarkLoast extends Beverage{

    public DarkLoast() {
        this.description = "다크 로스트";
    }

    @Override
    public double cost() {
        return .99;
    }
}
