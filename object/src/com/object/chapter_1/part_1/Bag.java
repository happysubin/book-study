package com.object.chapter_1.part_1;

public class Bag {

    private Long amount; //보유한 현금
    private Invitation invitation; //초대장
    private Ticket ticket; //티켓
    
    public Bag(Long amount){
        this(amount, null);
    }
 
    public Bag(Long amount, Invitation invitation) {
        this.amount = amount;
        this.invitation = invitation;
    }

    public boolean hasInvitation(){
        return invitation != null;
    }

    public boolean hasTicket(){
        return ticket != null;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
    
    public void minusAmount(Long amount){
        this.amount -= amount;
    }
    
    public void plusAmount(Long amount){
        this.amount += amount;
    }
}

