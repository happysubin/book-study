package com.code.code.chapter_7.v2;

import com.code.code.chapter_7.v1.Company;
import com.code.code.chapter_7.v1.Database;
import com.code.code.chapter_7.v1.MessageBus;

public class User {

    private int userId;
    private String email;
    private UserType type;

    public User(int userId, String email, UserType type) {
        this.userId = userId;
        this.email = email;
        this.type = type;
    }


    public int changeEmail(String newEmail, String companyEmailDomain, int numberOfEmployees) {
        if(this.email.equals(newEmail)){
            return numberOfEmployees;
        }

        String emailDomain = newEmail.split("@")[1];
        boolean isEmailCorporate = false;
        if(emailDomain.equals(companyEmailDomain)){
            isEmailCorporate = true;
        }
        UserType newType = isEmailCorporate ? UserType.Employee : UserType.Customer;

        if(this.type != newType){
            int delta = newType == UserType.Employee ? 1 : -1;
            int newNumber = numberOfEmployees + delta;
            numberOfEmployees = newNumber;
        }

        this.type = newType;
        this.email = newEmail;

        return numberOfEmployees;
    }

    enum UserType{
        Employee, Customer
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }
}
