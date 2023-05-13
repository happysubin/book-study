package com.code.code.chapter_7.v3;


import com.code.code.chapter_7.v2.MessageBus;

public class UserController {

    private final Database database = new Database();
    private final MessageBus messageBus = new MessageBus();

    public void changeEmail(int userId, String newEmail){
        //orm 쓴다고 가정
        User user = database.getUserById(userId);
        Company company = database.getCompany();

        user.changeEmail(newEmail, company);

        database.saveCompany(company);
        database.saveUser(user);
        messageBus.sendEmailChangedMessage(userId, newEmail);
    }

}
