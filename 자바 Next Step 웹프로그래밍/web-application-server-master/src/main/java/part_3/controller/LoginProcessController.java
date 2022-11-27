package part_3.controller;

import part_3.db.DataBase;
import part_3.model.user.User;
import part_3.webserver.HttpRequest;
import part_3.webserver.HttpResponse;

import java.io.DataOutputStream;
import java.io.IOException;

public class LoginProcessController implements Controller{
    @Override
    public void doProcess(HttpRequest httpRequest, DataOutputStream dos) throws IOException {
        String password = httpRequest.getParameter("password");
        String userId = httpRequest.getParameter("userId");
        User user = DataBase.findUserById(userId);

        HttpResponse response = new HttpResponse(dos);

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
