package com.object.chapter_1.part_3;

import com.object.chapter_1.part_1.Invitation;
import com.object.chapter_1.part_1.Ticket;

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

    private boolean hasInvitation(){
        return invitation != null;
    }

    private boolean hasTicket(){
        return ticket != null;
    }

    private void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    private void minusAmount(Long amount){
        this.amount -= amount;
    }

    private void plusAmount(Long amount){
        this.amount += amount;
    }

    public Long hold(Ticket ticket) {
        if(hasInvitation()){
            setTicket(ticket);
            return 0L;
        }
        minusAmount(ticket.getFee());
        setTicket(ticket);
        return ticket.getFee();
    }
}

