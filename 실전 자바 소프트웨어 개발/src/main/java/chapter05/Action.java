package chapter05;

@FunctionalInterface
public interface Action {
    void perform(Facts facts);
}
