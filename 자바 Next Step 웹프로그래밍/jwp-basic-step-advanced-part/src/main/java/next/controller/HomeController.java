package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.mvc.JspView;
import next.dao.QuestionDao;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;

//@Controller
//public class HomeController extends AbstractController {
//    private QuestionDao questionDao = QuestionDao.getInstance();
//
//    @Override
//    public ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        return jspView("home.jsp").addObject("questions", questionDao.findAll());
//    }
//
//    @RequestMapping(value = "/", method = RequestMethod.GET)
//    public ModelAndView execute(){
//        return jspView("home.jsp").addObject("questions", questionDao.findAll());
//
//    }
//}

@Controller
public class HomeController {

    private QuestionDao questionDao = QuestionDao.getInstance();

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView getHome(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(new JspView("home.jsp"));
    }
}