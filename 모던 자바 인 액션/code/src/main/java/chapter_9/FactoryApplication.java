package chapter_9;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class FactoryApplication {

    static public class ProductFactory{

        static final Map<String, Supplier<Product>> map = new HashMap<>();

        static{
            map.put("loan", Loan::new);
            map.put("stock", Stock::new);
            map.put("bond", Bond::new);
        }

        public static Product createProduct(String name){
            Supplier<Product> p = map.get(name);
            if(p != null) return p.get();
            throw new IllegalArgumentException("No such Product " + name);
        }
    }

    public static void main(String[] args) {
        Product bond = ProductFactory.createProduct("bond");
        System.out.println("bond = " + bond);
    }

    static abstract class Product{
    }

    static class Loan extends Product{
    }

    static class Stock extends Product{
    }

    static class Bond extends Product{
    }
}
