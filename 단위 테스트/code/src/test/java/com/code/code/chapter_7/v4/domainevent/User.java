package com.code.code.chapter_7.v4.domainevent;


import com.code.code.chapter_7.v4.PreCondition;

import java.util.ArrayList;
import java.util.List;

public class User {

    private int userId;
    private String email;
    private UserType type;
    private boolean isEmailConfirmed;
    private List<EmailChangedEvent> emailChangedEvents = new ArrayList<>();


    public User(int userId, String email, UserType type, boolean isEmailConfirmed) {
        this.userId = userId;
        this.email = email;
        this.type = type;
        this.isEmailConfirmed = isEmailConfirmed;
    }

    public void changeEmail(String newEmail, Company company) {

        PreCondition.requires(canChangeEmail() == null);

        if(this.email.equals(newEmail)){
            return ;
        }

        UserType newType = company.isEmailCorporate(newEmail) ? UserType.Employee : UserType.Customer;

        if(this.type != newType){
            int delta = newType == UserType.Employee ? 1 : -1;
            company.changeNumberOfEmployees(delta);
        }

        this.type = newType;
        this.email = newEmail;

        emailChangedEvents.add(new EmailChangedEvent(userId, newEmail));
    }

    /**
     * 검증 로직을 몽땅 모은다.
     */
    public String canChangeEmail() {
        if(isEmailConfirmed){
            return "Can't change a confirmed email";
        }
        return null;
    }

    enum UserType{
        Employee, Customer
    }

    public int getUserId() {\
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public UserType getType() {
        return type;
    }

    public boolean isEmailConfirmed() {
        return isEmailConfirmed;
    }

    public List<EmailChangedEvent> getEmailChangedEvents() {
        return emailChangedEvents;
    }
}
/**
 * 좋은 테스트 설계를 위해서는 좋은 프로덕션 코드를 가지고 있어야 한다.
 */
