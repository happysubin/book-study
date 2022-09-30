package com.object.chapter_6.part_total;

import java.time.Duration;
import java.time.LocalDateTime;

public class Event { //특정 일자에 실제로 발생하는 사건 -> 이벤트

    private String subject;
    private LocalDateTime from;
    private Duration duration;

    public Event(String subject, LocalDateTime from, Duration duration) {
        this.subject = subject;
        this.from = from;
        this.duration = duration;
    }

    public boolean isSatisfied(RecurringSchedule schedule){
        if(from.getDayOfWeek() != schedule.getDayOfWeek() ||
        !from.toLocalTime().equals(schedule.getFrom()) ||
        !duration.equals(schedule.getDuration()))
        {
            //reschedule(schedule); // 이게 버그를 유발하는 코드 이것을 지워야함 -> 순수한 쿼리가 됨.
            return false;
        }

        return true;
    }

    private void reschedule(RecurringSchedule schedule) {
        from = LocalDateTime.of(from.toLocalDate().plusDays(daysDistance(schedule)), schedule.getFrom());
        duration = schedule.getDuration();
    }

    private long daysDistance(RecurringSchedule schedule) {
        return schedule.getDayOfWeek().getValue() - from.getDayOfWeek().getValue();
    }


}
