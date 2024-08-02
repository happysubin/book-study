package com.object.book.chapter_1.part_3;

import com.object.book.chapter_1.part_1.Ticket;

public class Audience {

    private Bag bag;

    public Audience(Bag bag) {
        this.bag = bag;
    }

    public Long buy(Ticket ticket) {
        return bag.hold(ticket);
    }
}

