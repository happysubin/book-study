package com.code.code.chapter_7.v1;

public class User {

    private int userId;
    private String email;
    private UserType type;

    public User(int userId, String email, UserType type) {
        this.userId = userId;
        this.email = email;
        this.type = type;
    }

    public void changeEmail(int userId, String newEmail){
        User user = Database.getUserById(userId);

        if(user.getEmail().equals(newEmail)){
            return;
        }

        Company company = Database.getCompany();
        String emailDomain = newEmail.split("@")[1];
        boolean isEmailCorporate = false;
        if(company.getEmailDomain().equals(emailDomain)){
            isEmailCorporate = true;
        }

        UserType newType = isEmailCorporate ? UserType.Employee : UserType.Customer;

        if(user.getType() != newType){
            int delta = newType == UserType.Employee ? 1 : -1;
            int newNumber = company.getNumberOfEmployees() + delta;
            Database.saveCompany(newNumber);
        }

        this.userId = userId;
        this.type = newType;
        this.email = newEmail;

        Database.saveUser(this);
        MessageBus.sendEmailChangedMessage(this.userId, newEmail);
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
