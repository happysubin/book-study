package next.controller;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {

    private static Map<String, Controller> map = new HashMap<>();

    static{
        map.put("/user/create", new CreateUserController());
        map.put("/user/form", new ForwardController("/user/form.jsp"));
        map.put("/user/list", new ListUserController());
        map.put("/user/login", new LoginController());
        map.put("/user/login-form", new ForwardController("/user/login.jsp"));
        map.put("/user/logout", new LogoutController());
        map.put("/user/update", new UpdateUserController());
        map.put("/user/update-form", new UpdateUserFormController());
    }


    public static Controller getController(String path){
        return map.get(path);
    }
}
