package chapter_12;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Flock implements Quackable{

    private List<Quackable> ducks = new ArrayList<>();

    public void add(Quackable quackable){
        ducks.add(quackable);
    }

    @Override
    public void quack() {
        Iterator<Quackable> iterator = ducks.iterator();
        while(iterator.hasNext()){
            Quackable quackable = iterator.next();
            quackable.quack();
        }
    }

    @Override
    public void registerObserver(Observer observer) {
        for (Quackable duck : ducks) {
            duck.registerObserver(observer);
        }
    }

    @Override
    public void notifyObservers() {
        for (Quackable duck : ducks) {
            duck.notifyObservers();
        }
    }
}
