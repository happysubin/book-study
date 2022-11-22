package controller;

import model.user.User;
import model.user.UserFactory;
import util.HttpResponseUtils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

public class SaveUserController implements Controller{

    @Override
    public void doProcess(Map<String, String> requestMap, DataOutputStream dos) throws IOException {
        User user = UserFactory.createUser(requestMap);
        System.out.println("user.toString() = " + user.toString());
        HttpResponseUtils.response302Header(dos);
    }
}
