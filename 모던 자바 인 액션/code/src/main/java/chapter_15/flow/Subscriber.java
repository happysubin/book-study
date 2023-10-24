package chapter_15.flow;

public interface Subscriber<T> {
    void onNext(T t);
}
