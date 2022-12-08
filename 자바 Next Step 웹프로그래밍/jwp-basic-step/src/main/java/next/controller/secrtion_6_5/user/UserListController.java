package next.controller.secrtion_6_5.user;

import core.db.DataBase;
import core.mvc.section_6_5.Controller;
import core.mvc.section_8_3.JspView;
import core.mvc.section_8_3.ModelAndView;
import core.mvc.section_8_3.View;
import next.controller.secrtion_6_5.UserSessionUtils;
import next.dao.UserDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserListController implements Controller {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        if (!UserSessionUtils.isLogined(req.getSession())) {
            return new ModelAndView(new JspView("redirect:/users/loginForm"));
        }
        //req.setAttribute("users", DataBase.findAll());
        UserDao userDao = new UserDao();
        ModelAndView modelAndView = new ModelAndView(new JspView("/user/list.jsp"));
        modelAndView.setModelData("users", userDao.findAllUser());
        return modelAndView;
    }
}
