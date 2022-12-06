package next.controller.secrtion_6_5.user;

import core.db.DataBase;
import core.mvc.section_6_5.Controller;
import next.controller.secrtion_6_5.UserSessionUtils;
import next.dao.UserDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ListUserController implements Controller {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (!UserSessionUtils.isLogined(req.getSession())) {
            return "redirect:/users/loginForm";
        }

        UserDao userDao = new UserDao();
        req.setAttribute("users", userDao.findAllUser() );
        return "/user/list.jsp";
    }
}
