package com.object.book.부록_C;

public  class Troll extends Monster{

    public Troll() {
        super(48);
    }


    @Override
    public String getAttack() {
        return "트롤은 곤봉으로 때린다";
    }
}

