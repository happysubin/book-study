package com.object.chapter_6.part_total;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Application {
    public static void main(String[] args) {
        Event meeting = new Event(
                "회의",
                LocalDateTime.of(2019, 5, 8, 10, 30),
                Duration.ofMinutes(30)
        );

        RecurringSchedule sc = new RecurringSchedule(
                "회의",
                DayOfWeek.MONDAY,
                LocalTime.of(10, 30),
                Duration.ofMinutes(30)
        );

    }
}
