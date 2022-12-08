package next.controller.secrtion_6_5.qna;

import core.mvc.section_6_5.Controller;
import core.mvc.section_8_3.JspView;
import core.mvc.section_8_3.ModelAndView;
import core.mvc.section_8_3.View;
import next.dao.AnswerDao;
import next.dao.QuestionDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowController implements Controller {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        Long questionId = Long.parseLong(req.getParameter("questionId"));
        QuestionDao questionDao = new QuestionDao();
        AnswerDao answerDao = new AnswerDao();

        //req.setAttribute("question", questionDao.findById(questionId));
        //req.setAttribute("answers", answerDao.findAllByQuestionId(questionId));

        ModelAndView modelAndView = new ModelAndView(new JspView("/qna/show.jsp"));
        modelAndView.setModelData("question", questionDao.findById(questionId));
        modelAndView.setModelData("answers", answerDao.findAllByQuestionId(questionId));
        return modelAndView;
    }
}


