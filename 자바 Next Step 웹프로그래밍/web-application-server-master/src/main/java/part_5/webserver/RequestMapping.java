package part_5.webserver;

import part_5.controller.Controller;
import part_5.controller.CreateUserController;
import part_5.controller.ListUserController;
import part_5.controller.LoginController;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {

    public static Map<String, Controller> controllers = new HashMap<>();

    static {
        controllers.put("/user/create", new CreateUserController());
        controllers.put("/user/list", new ListUserController());
        controllers.put("/user/login", new LoginController());
    }

    public static Controller getController(String requestUrl){
        return controllers.get(requestUrl);
    }
}
