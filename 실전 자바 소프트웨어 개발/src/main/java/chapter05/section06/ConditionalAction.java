package chapter05.section06;

import chapter05.Facts;

public interface ConditionalAction {

    boolean evaluate(Facts facts);
    void perform(Facts facts);
}
