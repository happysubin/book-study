package chapter_9;

import java.util.ArrayList;
import java.util.List;

public class ObserverApplication {

    public static void main(String[] args) {
        ObserverApplication application = new ObserverApplication();
        application.execute();
        application.executeWithLabmda();
    }

    private void execute() {

        Feed feed = new Feed();
        feed.registerObserver(new NYTimes());
        feed.registerObserver(new Guardian());
        feed.registerObserver(new LeMonde());
        feed.notifyObservers("The queen said her favorite book is Modern Java in Action!");
    }

    private void executeWithLabmda() {

        Feed feed = new Feed();
        feed.registerObserver((tweet)-> {
            if(tweet != null && tweet.contains("money")){
                System.out.println("Breaking news in NY! " + tweet);
            }
        });

        feed.registerObserver((tweet -> {
            if(tweet != null && tweet.contains("queen")){
                System.out.println("Yet more news from London ... " + tweet);
            }
        }));

        feed.notifyObservers("The queen said her favorite book is Modern Java in Action!");

    }

    @FunctionalInterface
    interface Observer{
        void notify(String tweet);
    }

    class NYTimes implements Observer{

        @Override
        public void notify(String tweet) {
            if(tweet != null && tweet.contains("money")){
                System.out.println("Breaking new in NY! " + tweet);
            }
        }
    }

    class Guardian implements Observer{

        @Override
        public void notify(String tweet) {
            if(tweet != null && tweet.contains("queen")){
                System.out.println("Yet more news from London... " + tweet);
            }
        }
    }

    class LeMonde implements Observer{

        @Override
        public void notify(String tweet) {
            if(tweet != null && tweet.contains("queen")){
                System.out.println("Today cheese, wine and news" + tweet);
            }
        }
    }

    interface Subject{
        void registerObserver(Observer o);
        void notifyObservers(String tweet);
    }

    class Feed implements Subject{

        private final List<Observer> observers = new ArrayList<>();

        @Override
        public void registerObserver(Observer o) {
            this.observers.add(o);
        }

        @Override
        public void notifyObservers(String tweet) {
            observers.forEach(o -> o.notify(tweet));
        }
    }
}
