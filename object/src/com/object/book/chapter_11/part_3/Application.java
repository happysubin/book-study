package com.object.book.chapter_11.part_3;

import java.time.Duration;

public class Application {
    public static void main(String[] args) {
        new Phone(
                new TaxablePolicy(
                0.05,
                new RateDiscountablePolicy(
                        new RegularPolicy(Money.wons(1000), Duration.ofSeconds(10) ),
                        Money.wons(1000)
                )
        ));
    }
}

/**
 * 합성을 통해 클래스 폭발 문제처럼 너무 많은 클래스를 생성하지 않고도 다양한 조합이 가능하다.
 *
 */