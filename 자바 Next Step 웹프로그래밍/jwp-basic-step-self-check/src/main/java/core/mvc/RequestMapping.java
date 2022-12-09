package core.mvc;

import java.util.HashMap;
import java.util.Map;

import core.jdbc.JdbcTemplate;
import next.controller.HomeController;
import next.controller.qna.*;
import next.controller.user.CreateUserController;
import next.controller.user.ListUserController;
import next.controller.user.LoginController;
import next.controller.user.LogoutController;
import next.controller.user.ProfileController;
import next.controller.user.UpdateFormUserController;
import next.controller.user.UpdateUserController;

import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.dao.UserDao;
import next.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RequestMapping {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private Map<String, Controller> mappings = new HashMap<>();

    void initMapping() {

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        UserDao userDao = new UserDao(jdbcTemplate);
        QuestionDao questionDao = new QuestionDao(jdbcTemplate);
        AnswerDao answerDao = new AnswerDao(jdbcTemplate);
        QuestionService questionService = new QuestionService(questionDao, userDao);

        mappings.put("/", new HomeController(questionDao));
        mappings.put("/users/form", new ForwardController("/user/form.jsp"));
        mappings.put("/users/loginForm", new ForwardController("/user/login.jsp"));
        mappings.put("/users", new ListUserController(userDao));
        mappings.put("/users/login", new LoginController(userDao));
        mappings.put("/users/profile", new ProfileController(userDao));
        mappings.put("/users/logout", new LogoutController());
        mappings.put("/users/create", new CreateUserController(userDao));
        mappings.put("/users/updateForm", new UpdateFormUserController(userDao));
        mappings.put("/users/update", new UpdateUserController(userDao));
        mappings.put("/qna/form", new ForwardController("/qna/form.jsp"));
        mappings.put("/qna/show", new ShowController(questionDao, answerDao));
        mappings.put("/api/qna/addAnswer", new AddAnswerController(answerDao, questionDao));
        mappings.put("/api/qna/deleteAnswer", new DeleteAnswerController(answerDao));
        mappings.put("/qna/create", new CreateQuestionController(questionDao));
        mappings.put("/api/qna/list", new ListQuestionController(questionDao));
        mappings.put("/qna/updateForm", new UpdateFormQuestionController(questionDao));
        mappings.put("/qna/update", new UpdateQuestionController(questionDao));
        mappings.put("/api/qna/delete", new DeleteQuestionRestController(questionService));
        mappings.put("/qna/delete", new DeleteQuestionController(questionService));

        logger.info("Initialized Request Mapping!");
    }

    public Controller findController(String url) {
        return mappings.get(url);
    }

    void put(String url, Controller controller) {
        mappings.put(url, controller);
    }

}
