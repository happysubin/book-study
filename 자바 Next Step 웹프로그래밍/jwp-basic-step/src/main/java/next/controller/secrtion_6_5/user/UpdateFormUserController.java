package next.controller.secrtion_6_5.user;

import core.db.DataBase;
import core.mvc.section_6_5.Controller;
import core.mvc.section_8_3.JspView;
import core.mvc.section_8_3.ModelAndView;
import core.mvc.section_8_3.View;
import next.controller.secrtion_6_5.UserSessionUtils;
import next.dao.UserDao;
import next.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateFormUserController implements Controller {

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        String userId = req.getParameter("userId");
        UserDao userDao = new UserDao();
        User user = userDao.findByUserId(userId);
        if (!UserSessionUtils.isSameUser(req.getSession(), user)) {
            throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }
        ModelAndView modelAndView = new ModelAndView(new JspView("/user/update.jsp"));
        modelAndView.setModelData("user", user);
        return modelAndView;
    }
}