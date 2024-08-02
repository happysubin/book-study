package com.object.book.chapter_5.step_1;

import java.time.LocalDateTime;

public class Screening {

    public Movie movie; // 가격을 계싼하라는 메시지를 전송해야 하므로 영화에 대한 참조를 가지고 있어야 한다.
    public int sequence;
    public LocalDateTime whenScreened;

    /**
     *
     * 상영 객체는 예매하라 메시지에 응답할 수 있어야함.
     * 영화를 예매하고 예매 인스턴스를 생성할 책임을 가진다.
     *
     */
    public Reservation reserve(Customer customer, int audienceCount){
        return new Reservation(customer, this, calculateFee(audienceCount), audienceCount);
    }

    /**
     *
     * Movie에 전송하는 메시지의 시그니처를 calculateMovieFee(Screening screening)으로 선언한 사실에 주목해야한다.
     * Screening이 Movie의 내부 구현에 대한 어떤 지식도 없이 전송할 메시지를 결정했다는 것에 주목해야한다.
     * 이처럼 Movie의 구현을 고려하지 않고 필요한 메시지를 결정하면 Movie의 내부 구현을 깔끔하게 캡슐화 할 수 있다.
     */
    private Money calculateFee(int audienceCount){
        return movie.calculateMovieFee(this).times(audienceCount);
    }

    public int getSequence() {
        return sequence;
    }

    public LocalDateTime getWhenScreened() {
        return whenScreened;
    }
}
