package chapter_9;

import java.util.function.Consumer;
import java.util.regex.Pattern;

public class TemplateMethodApplication {

    public static void main(String[] args) {

        TemplateMethodApplication application = new TemplateMethodApplication();
        application.execute();
    }

    public void execute(){

        OnlineBacking onlineBacking = new OnlineBacking() {

            @Override
            void makeCustomerHappy(Customer c) {
                System.out.println("Hello" + c);
            }
        };

        new OnlineBackingLambda().processCustomer(1, (c) -> {
            System.out.println("Hello" + c);
        });
    }


    abstract class OnlineBacking{
        public void processCustomer(int id, Consumer<Customer> makeCustomerHappy){
            Customer c = Database.getCustomerWithId(id);
            makeCustomerHappy.accept(c);
        }

        abstract void makeCustomerHappy(Customer c);
    }

    class OnlineBackingLambda{
        public void processCustomer(int id, Consumer<Customer> makeCustomerHappy){
            Customer c = Database.getCustomerWithId(id);
            makeCustomerHappy.accept(c);
        }
    }


    static class Database{

        public static Customer getCustomerWithId(int id) {
            return new Customer();
        }
    }

    static class Customer{

    }
}
