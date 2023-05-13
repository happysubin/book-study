package com.code.code.chapter_7.v4.domainevent;

public class Company {

    private String emailDomain;
    private int numberOfEmployees;

    public void changeNumberOfEmployees(int delta){
        this.numberOfEmployees += delta;
    }

    public boolean isEmailCorporate(String email){
        String domain = email.split("@")[1];

        if(emailDomain.equals(domain)){
            return true;
        }
        return false;
    }


    public String getEmailDomain() {
        return emailDomain;
    }

    public void setEmailDomain(String emailDomain) {
        this.emailDomain = emailDomain;
    }

    public int getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public void setNumberOfEmployees(int numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }
}
