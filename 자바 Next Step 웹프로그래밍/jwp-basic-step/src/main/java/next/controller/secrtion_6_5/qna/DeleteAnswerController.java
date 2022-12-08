package next.controller.secrtion_6_5.qna;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.mvc.section_6_5.Controller;
import core.mvc.section_8_3.JsonView;
import core.mvc.section_8_3.ModelAndView;
import core.mvc.section_8_3.View;
import next.dao.AnswerDao;
import next.model.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class DeleteAnswerController implements Controller {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        Long answerId = Long.parseLong(req.getParameter("answerId"));
        AnswerDao answerDao = new AnswerDao();
        answerDao.delete(answerId);

//        ObjectMapper mapper = new ObjectMapper();
//        resp.setContentType("application/json;charset=UTF-8");
//        PrintWriter out = resp.getWriter();
//        out.print(mapper.writeValueAsString(Result.ok()));

        return new ModelAndView(new JsonView());
    }
}