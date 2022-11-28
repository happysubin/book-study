package part_5.controller;

import part_5.db.DataBase;
import part_5.model.user.User;
import part_5.webserver.HttpRequest;
import part_5.webserver.HttpResponse;

public class CreateUserController extends AbstractController{

    @Override
    public void doPost(HttpRequest request, HttpResponse response) {
        User user = new User(
                request.getParams("userId"),
                request.getParams("password"),
                request.getParams("name"),
                request.getParams("email")
        );
//        log.debug("user : {}", user);
        DataBase.addUser(user);
        response.sendRedirect("/index.html");
    }
}
