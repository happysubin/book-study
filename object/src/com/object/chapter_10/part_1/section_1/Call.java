package com.object.chapter_10.part_1.section_1;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * 개별 통화 기간을 저장하는 Call 클래스
 * from: 통화 시작 시간
 * to: 통화 종료 시간
 */

public class Call {

    private LocalDateTime from;
    private LocalDateTime to;

    public Call(LocalDateTime from, LocalDateTime to) {
        this.from = from;
        this.to = to;
    }

    public Duration getDuration(){
        return Duration.between(from, to);
    }

    public LocalDateTime getFrom() {
        return from;
    }
}
