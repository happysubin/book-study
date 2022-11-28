package part_5.controller;

import part_5.db.DataBase;
import part_5.model.user.User;
import part_5.webserver.HttpRequest;
import part_5.webserver.HttpResponse;

public class LoginController extends AbstractController {

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) {
        User user = DataBase.findUserById(request.getParams("userId"));
        if (user != null) {
            if (user.login(request.getParams("password"))) {
                response.addHeader("Set-Cookie", "logined=true");
                response.sendRedirect("/index.html");
            } else {
                response.sendRedirect( "/user/login_failed.html");
            }
        } else {
            response.sendRedirect("/user/login_failed.html");
        }
    }
}
