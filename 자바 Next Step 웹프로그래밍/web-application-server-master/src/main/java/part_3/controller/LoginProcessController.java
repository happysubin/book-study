package part_3.controller;

import part_3.db.DataBase;
import part_3.model.user.User;
import part_3.webserver.HttpRequest;
import part_3.webserver.HttpResponse;

import java.io.IOException;

public class LoginProcessController extends AbstractController{

    @Override
    protected void doPost(HttpRequest request, HttpResponse response) throws IOException {
        String password = request.getParameter("password");
        String userId = request.getParameter("userId");
        User user = DataBase.findUserById(userId);

        if(user == null){
            response.addHeader("Set-Cookie", "logined=false");
            response.sendRedirect("/user/login_failed.html");
        }

        else if(samePassword(password, user.getPassword())){
            response.addHeader("Set-Cookie", "logined=true");
            response.sendRedirect("/index.html");
        }
        else{
            response.addHeader("Set-Cookie", "logined=false");
            response.sendRedirect("/user/login_failed.html");
        }
    }

    private boolean samePassword(String password, String checkPassword) {
        return password.equals(checkPassword);
    }
}
