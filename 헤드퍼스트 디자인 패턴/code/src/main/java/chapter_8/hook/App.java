package chapter_8.hook;

public class App {

    public static void main(String[] args) {
        TeaWithHook tea = new TeaWithHook();
        CoffeeWithHook coffee = new CoffeeWithHook();

        tea.prepareRecipe();
        System.out.println();
        coffee.prepareRecipe();
    }
}
