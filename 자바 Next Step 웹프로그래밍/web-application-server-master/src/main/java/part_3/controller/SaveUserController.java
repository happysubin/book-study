package part_3.controller;

import part_3.db.DataBase;
import part_3.model.user.User;
import part_3.model.user.UserFactory;
import part_3.util.HttpResponseUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

public class SaveUserController implements Controller{

    @Override
    public void doProcess(Map<String, String> requestMap, DataOutputStream dos) throws IOException {
        User user = UserFactory.createUser(requestMap);
        DataBase.addUser(user);
        HttpResponseUtils.response302Header(dos);
    }
}
