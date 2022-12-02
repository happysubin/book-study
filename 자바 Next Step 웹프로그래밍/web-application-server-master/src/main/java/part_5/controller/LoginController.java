package part_5.controller;

import part_5.db.DataBase;
import part_5.model.user.User;
import part_5.webserver.HttpRequest;
import part_5.webserver.HttpResponse;
import part_6.HttpSession;

public class LoginController extends AbstractController {

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) {
        User user = DataBase.findUserById(request.getParams("userId"));
        if (user != null) {
            if (user.login(request.getParams("password"))) {
                //response.addHeader("Set-Cookie", "logined=true");
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                response.sendRedirect("/index.html");
            } else {
                response.sendRedirect( "/user/login_failed.html");
            }
        } else {
            response.sendRedirect("/user/login_failed.html");
        }
    }
}
