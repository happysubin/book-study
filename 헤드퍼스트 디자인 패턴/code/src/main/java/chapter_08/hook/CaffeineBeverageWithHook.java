package chapter_08.hook;

public abstract class CaffeineBeverageWithHook {

    final void prepareRecipe(){
        boilWater();
        brew();
        pourInCup();
        if(customerWantsCondiments()){
            addCondiments();
        }
    }

    protected abstract void addCondiments();

    protected abstract void brew();

    private void pourInCup() {
        System.out.println("컵에 따르는 중");
    }

    private void boilWater() {
        System.out.println("물 끓이는 중");
    }

    protected boolean customerWantsCondiments() {
        return true;
    }
}
