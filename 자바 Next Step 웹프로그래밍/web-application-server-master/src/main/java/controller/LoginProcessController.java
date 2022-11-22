package controller;

import db.DataBase;
import model.user.User;
import util.HttpResponseUtils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

import static webserver.RequestHandler.PATH;

public class LoginProcessController implements Controller{
    @Override
    public void doProcess(Map<String, String> requestMap, DataOutputStream dos) throws IOException {
        String password = requestMap.get("password");
        String userId = requestMap.get("userId");
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
