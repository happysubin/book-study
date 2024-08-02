package com.object.book.chapter_9.part_2;

import com.object.book.chapter_2.part_total.Money;
import com.object.book.chapter_2.part_total.Movie;
import com.object.book.chapter_9.part_2.Factory;

public class Client {

    private Factory factory;

    public Client(Factory factory) {
        this.factory = factory;
    }

    public Money getAvatarFee(){
        Movie avatar = factory.createAvatarMovie();
        return avatar.getFee();
    }
}
