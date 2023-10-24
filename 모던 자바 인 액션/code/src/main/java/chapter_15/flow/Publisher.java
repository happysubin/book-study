package chapter_15.flow;

public interface Publisher<T> {
    void subscribe(Subscriber<? super T> subscriber);
}
