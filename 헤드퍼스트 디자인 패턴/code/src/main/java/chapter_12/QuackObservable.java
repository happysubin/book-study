package chapter_12;

public interface QuackObservable {
    void registerObserver(Observer observer);
    void notifyObservers();
}
