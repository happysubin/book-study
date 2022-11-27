package part_3.controller;

import part_3.db.DataBase;
import part_3.model.user.User;
import part_3.model.user.UserFactory;
import part_3.webserver.HttpRequest;
import part_3.webserver.HttpResponse;

import java.io.DataOutputStream;
import java.io.IOException;

public class SaveUserController implements Controller{

    private static String PASSWORD = "password";
    private static String NAME = "name";
    private static String USERID = "userId";
    private static String EMAIL = "email";

    @Override
    public void doProcess(HttpRequest httpRequest, DataOutputStream dos) throws IOException {
        User user = UserFactory.createUser(
                httpRequest.getParameter(USERID),
                httpRequest.getParameter(PASSWORD),
                httpRequest.getParameter(NAME),
                httpRequest.getParameter(EMAIL)
        );
        DataBase.addUser(user);
        HttpResponse response = new HttpResponse(dos);
        response.sendRedirect("/index.html");
    }
}
