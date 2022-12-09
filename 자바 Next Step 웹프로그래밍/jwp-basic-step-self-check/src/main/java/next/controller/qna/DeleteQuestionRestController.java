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

public class DeleteQuestionRestController extends AbstractController {

    private final QuestionService questionService;

    public DeleteQuestionRestController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        User user = UserSessionUtils.getUserFromSession(session);

        if(user == null){
            return jsonView().addObject("status", Result.fail("유저 객체가 없습니다.."));
        }

        if(!UserSessionUtils.isLogined(session)){
            return jsonView().addObject("status", Result.fail("로그인을 해야합니다."));
        }

        String questionId = request.getParameter("questionId");

        questionService.execute(Long.valueOf(questionId), user.getUserId());
        return jsonView().addObject("status", Result.ok());
    }
}
