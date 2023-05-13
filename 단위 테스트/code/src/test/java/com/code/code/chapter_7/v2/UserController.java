package com.code.code.chapter_7.v2;

public class UserController {

    private final Database database = new Database();
    private final MessageBus messageBus = new MessageBus();

    public void changeEmail(int userId, String newEmail){
        User user = database.getUserById(userId);
        Company company = database.getCompany();

        int newNumberOfEmployees = user.changeEmail(newEmail, company.getEmailDomain(), company.getNumberOfEmployees());

        database.saveCompany(newNumberOfEmployees);
        database.saveUser(user);
        messageBus.sendEmailChangedMessage(userId, newEmail);
    }

}
