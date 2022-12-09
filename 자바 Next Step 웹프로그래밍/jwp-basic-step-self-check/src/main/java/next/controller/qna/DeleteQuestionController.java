package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.model.Result;
import next.model.User;
import next.service.QuestionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteQuestionController extends AbstractController {

    private final QuestionService questionService;

    public DeleteQuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        User user = UserSessionUtils.getUserFromSession(session);

        if(user == null){
            return jspView("redirect:/");
        }

        if(!UserSessionUtils.isLogined(session)){
            return jspView("redirect:/");
        }

        String questionId = request.getParameter("questionId");
        questionService.execute(Long.valueOf(questionId), user.getUserId());

        return jspView("redirect:/");
    }
}
