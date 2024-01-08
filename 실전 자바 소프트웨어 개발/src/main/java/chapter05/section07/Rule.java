package chapter05.section07;

import chapter05.Facts;

@FunctionalInterface
public interface Rule {
    void perform(Facts facts);
}
