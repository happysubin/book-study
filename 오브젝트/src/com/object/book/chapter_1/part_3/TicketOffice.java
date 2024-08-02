package com.object.book.chapter_1.part_3;

import com.object.book.chapter_1.part_1.Ticket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TicketOffice {

    private Long amount;
    private List<Ticket> tickets = new ArrayList<>();

    public TicketOffice(Long amount, Ticket ... tickets) {
        this.amount = amount;
        this.tickets.addAll(Arrays.asList(tickets));
    }

    public void sellTicketTo(Audience audience) {
        plusAmount(audience.buy(getTicket()));
    }

    private Ticket getTicket() {
        return tickets.remove(0);
    }

    private void plusAmount(Long amount){
        this.amount += amount;
    }

    private void minusAmount(Long amount){
        this.amount -= amount;
    }
}

/**
 * 객체지향적으로 수정하는데 TicketOffice에서 Audience에 대한 의존관계가 생김.
 * 이럴 때 트레이드 오프를 해야한다. 어떻게 설계를 할 지 결정하자.
 */