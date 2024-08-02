package com.object.book.chapter_10.part_1.section_1;

import java.time.Duration;
import java.time.LocalDateTime;

public class Application {

    public static void main(String[] args) {
        Phone phone = new Phone(Money.wons(5), Duration.ofSeconds(10));
        
        phone.call(new Call(
                LocalDateTime.of(2018, 1, 1, 12, 10, 0), 
                LocalDateTime.of(2018, 1, 1, 12, 11, 0)));



        phone.call(new Call(
                LocalDateTime.of(2018, 1, 1, 12, 10, 0),
                LocalDateTime.of(2018, 1, 1, 12, 11, 0)));

        System.out.println("phone.calculateFee().getAmount() = " + phone.calculateFee().getAmount()); //60
    }
}
