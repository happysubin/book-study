package tobyspring.vol1.callbacktemplate;

public interface LineCallback<T> {
    T doSomethingWithLine(String line, T value);
}
