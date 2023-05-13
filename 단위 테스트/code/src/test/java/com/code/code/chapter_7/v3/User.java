package com.code.code.chapter_7.v3;

public class User {

    private int userId;
    private String email;
    private UserType type;

    public User(int userId, String email, UserType type) {
        this.userId = userId;
        this.email = email;
        this.type = type;
    }


    public void changeEmail(String newEmail, Company company) {

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

    }

    enum UserType{
        Employee, Customer
    }
}
/**
 * 좋은 테스트 설계를 위해서는 좋은 프로덕션 코드를 가지고 있어야 한다.
 */
