package next.controller.secrtion_6_5.qna;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.mvc.section_6_5.Controller;
import core.mvc.section_8_3.JsonView;
import core.mvc.section_8_3.ModelAndView;
import core.mvc.section_8_3.View;
import next.dao.AnswerDao;
import next.model.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class AddAnswerController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(AddAnswerController.class);

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        Answer answer = new Answer(
                req.getParameter("writer"),
                req.getParameter("contents"),
                Long.parseLong(req.getParameter("questionId")));
        log.debug("answer : {}", answer);

        AnswerDao answerDao = new AnswerDao();
        Answer savedAnswer = answerDao.insert(answer);

        ModelAndView modelAndView = new ModelAndView(new JsonView());
        modelAndView.setModelData("savedAnswer", savedAnswer);
        return modelAndView;
    }
}