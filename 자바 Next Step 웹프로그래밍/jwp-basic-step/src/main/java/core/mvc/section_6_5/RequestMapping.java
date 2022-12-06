package core.mvc.section_6_5;

import next.controller.secrtion_6_5.*;
import next.controller.secrtion_6_5.qna.AddAnswerController;
import next.controller.secrtion_6_5.qna.DeleteAnswerController;
import next.controller.secrtion_6_5.qna.ShowController;
import next.controller.secrtion_6_5.user.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private Map<String, Controller> mappings = new HashMap<>();

    void initMapping() {
        mappings.put("/", new HomeController());
        mappings.put("/user/form", new ForwardController("/user/form.jsp"));
        mappings.put("/qna/form", new ForwardController("/qna/form.jsp"));
        mappings.put("/user/login-form", new ForwardController("/user/login.jsp"));
        mappings.put("/user/list", new ListUserController());
        mappings.put("/user/login", new LoginController());
        mappings.put("/user/profile", new ProfileController());
        mappings.put("/user/logout", new LogoutController());
        mappings.put("/user/create", new CreateUserController());
        mappings.put("/user/update-form", new UpdateFormUserController());
        mappings.put("/user/update", new UpdateUserController());
        mappings.put("/qna/show", new ShowController());
        mappings.put("/api/qna/addAnswer", new AddAnswerController());
        mappings.put("/api/qna/deleteAnswer", new DeleteAnswerController());

        logger.info("Initialized Request Mapping!");
    }

    public Controller findController(String url) {
        return mappings.get(url);
    }

    void put(String url, Controller controller) {
        mappings.put(url, controller);
    }
}
