package next.controller.secrtion_6_5.user;

import core.mvc.section_6_5.Controller;
import core.mvc.section_8_3.JspView;
import core.mvc.section_8_3.ModelAndView;
import core.mvc.section_8_3.View;
import next.controller.secrtion_6_5.UserSessionUtils;
import next.dao.UserDao;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateUserController implements Controller {
    private static final Logger log = LoggerFactory.getLogger(UpdateUserController.class);

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        UserDao userDao = new UserDao();
        User user = userDao.findByUserId(req.getParameter("userId"));
        if (!UserSessionUtils.isSameUser(req.getSession(), user)) {
            throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }

        User updateUser = new User(
                req.getParameter("userId"),
                req.getParameter("password"),
                req.getParameter("name"),
                req.getParameter("email"));
        log.debug("Update User : {}", updateUser);
        userDao.update(updateUser);

        return new ModelAndView(new JspView( "redirect:/"));
    }
}
