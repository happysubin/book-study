package part_3.controller;

import part_3.db.DataBase;
import part_3.model.user.User;
import part_3.util.HttpResponseUtils;
import part_5.webserver.HttpRequest;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public class LoginProcessController implements Controller{
    @Override
    public void doProcess(HttpRequest httpRequest, DataOutputStream dos) throws IOException {
        String password = httpRequest.getParameter("password");
        String userId = httpRequest.getParameter("userId");
        User user = DataBase.findUserById(userId);

        if(samePassword(password, user.getPassword())){
            HttpResponseUtils.responseLoginSuccessHeader(dos);
        }
        else{
            HttpResponseUtils.responseLoginFailHeader(dos);
        }
    }

    private boolean samePassword(String password, String checkPassword) {
        return password.equals(checkPassword);
    }
}
