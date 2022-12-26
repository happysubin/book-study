package chapter_08.basic;

public class App {
    public static void main(String[] args) {
        CaffeineBeverage coffee = new Coffee();
        coffee.prepareRecipe();
        System.out.println();
        CaffeineBeverage tea = new Tea();
        tea.prepareRecipe();
    }
}
