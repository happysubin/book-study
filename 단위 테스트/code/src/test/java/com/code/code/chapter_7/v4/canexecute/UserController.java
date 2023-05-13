package com.code.code.chapter_7.v4.canexecute;


import com.code.code.chapter_7.v2.MessageBus;

public class UserController {

    private final Database database = new Database();
    private final MessageBus messageBus = new MessageBus();

    public String changeEmail(int userId, String newEmail){
        //orm 쓴다고 가정
        User user = database.getUserById(userId);

        user.canChangeEmail();

        Company company = database.getCompany();

        user.changeEmail(newEmail, company);

        database.saveCompany(company);
        database.saveUser(user);
        messageBus.sendEmailChangedMessage(userId, newEmail);
        return "OK";

    }

}
