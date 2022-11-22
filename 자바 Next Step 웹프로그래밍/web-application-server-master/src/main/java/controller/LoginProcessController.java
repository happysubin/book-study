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
            byte[] body = Files.readAllBytes(new File("./webapp/index.html").toPath());
            HttpResponseUtils.responseLoginSuccessHeader(dos, body.length);
//            HttpResponseUtils.responseBody(dos, body);
        }
        else{
            byte[] body = Files.readAllBytes(new File("./webapp/user/login_failed.html").toPath());
            HttpResponseUtils.responseLoginFailHeader(dos, body.length);
//            HttpResponseUtils.responseBody(dos, body);
        }
    }

    private boolean samePassword(String password, String checkPassword) {
        return password.equals(checkPassword);
    }
}
