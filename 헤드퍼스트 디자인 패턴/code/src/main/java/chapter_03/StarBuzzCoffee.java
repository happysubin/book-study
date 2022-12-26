package chapter_03;

public class StarBuzzCoffee {

    public static void main(String[] args) {
        Beverage beverage = new Espresso();
        System.out.println("beverage description = " + beverage.getDescription());
        System.out.println("beverage cost = " + beverage.cost());

        Beverage beverage2 = new DarkLoast();
        beverage2 = new Mocha(beverage2); //모카로 감싼다.
        beverage2 = new Mocha(beverage2); //모카로 감싼다.
        beverage2 = new Whip(beverage2); //휘핑 크림으로 감싼다.
        System.out.println("beverage2.getDescription() = " + beverage2.getDescription());
        System.out.println("beverage2 cost = " + beverage2.cost());


        Beverage beverage3 = new HouseBlend();
        beverage3 = new Soy(beverage3);
        beverage3 = new Mocha(beverage3);
        beverage3= new Whip(beverage3);
        System.out.println("beverage3 description = " + beverage3.getDescription());
        System.out.println("beverage3 cost = " + beverage3.cost());
    }

}
