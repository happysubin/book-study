package chapter_04.factorymethod;

public class App {

    public static void main(String[] args) {
        PizzaStore nyPizzaStore = new NYPizzaStore();
        PizzaStore chicagoPizzaStore = new ChicagoPizzaStore();

        Pizza pizza1 = nyPizzaStore.createPizza("cheese");
        System.out.println(pizza1.getName());

        Pizza pizza2 = chicagoPizzaStore.createPizza("cheese");
        System.out.println(pizza2.getName());
    }
}
