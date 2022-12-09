package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.QuestionDao;
import next.model.Question;
import next.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateFormQuestionController extends AbstractController {

    private QuestionDao questionDao;

    public UpdateFormQuestionController(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if(user == null){
            return jspView("redirect:/");
        }

        if(!UserSessionUtils.isLogined(session)){
            return jspView("redirect:/");
        }

        Long questionId = Long.valueOf(request.getParameter("questionId"));
        Question question = questionDao.findById(questionId);

        if(!question.isWriter(user)){
            return jspView("redirect:/");
        }

        return jspView("/qna/updateForm.jsp").addObject("question", question);
    }
}
