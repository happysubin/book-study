package chapter_03;

/**
 * Beverage 객체가 들어갈 자리에 들어갈 수 있어야 하므로 Beverage 클래스를 확장한다.
 */

public abstract class CondimentDecorator extends Beverage{

    /**
     * 각 데코레이터가 감쌀 음료를 나타내는 Beverage 객체를 여기에서 지정한다.
     * 음료를 지정할 때는 데코레이터에서 어떤 음료를 감쌀 수 있도록 Beverage 슈퍼클래스 유형을 사용한다.
     */
    protected Beverage beverage;

    /**
     * 모든 첨가물 데코리어텡 getDescription() 메서드를 새로 구현하도록 만들 계획. 그래서 추상 메서드를 선언.
     */
    public abstract String getDescription();
}
