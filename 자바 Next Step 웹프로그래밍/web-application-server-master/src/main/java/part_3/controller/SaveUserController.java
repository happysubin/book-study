package part_3.controller;

import part_3.db.DataBase;
import part_3.model.user.User;
import part_3.model.user.UserFactory;
import part_3.webserver.HttpRequest;
import part_3.webserver.HttpResponse;

import java.io.IOException;

public class SaveUserController extends AbstractController{

    private static String PASSWORD = "password";
    private static String NAME = "name";
    private static String USERID = "userId";
    private static String EMAIL = "email";

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) throws IOException {
        User user = UserFactory.createUser(
                request.getParameter(USERID),
                request.getParameter(PASSWORD),
                request.getParameter(NAME),
                request.getParameter(EMAIL)
        );
        DataBase.addUser(user);
        response.sendRedirect("/index.html");
    }
}
