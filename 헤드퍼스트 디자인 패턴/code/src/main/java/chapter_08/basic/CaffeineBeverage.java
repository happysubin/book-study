package chapter_08.basic;

public abstract class CaffeineBeverage {

    final void prepareRecipe(){
        boilWater();
        brew();
        pourInCup();
        addCondiments();
    }

    protected abstract void addCondiments();

    protected abstract void brew();

    private void pourInCup() {
        System.out.println("컵에 따르는 중");
    }

    private void boilWater() {
        System.out.println("물 끓이는 중");
    }
}
