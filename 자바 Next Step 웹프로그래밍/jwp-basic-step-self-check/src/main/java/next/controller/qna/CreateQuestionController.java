package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CreateQuestionController extends AbstractController {

    private QuestionDao questionDao;

    public CreateQuestionController(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if(user == null){
            return jspView("redirect:/");
        }


        Question question = new Question(
                user.getUserId(),
                req.getParameter("title"),
                req.getParameter("contents")
        );

        questionDao.insert(question);
        return jspView("redirect:/");
    }
}
