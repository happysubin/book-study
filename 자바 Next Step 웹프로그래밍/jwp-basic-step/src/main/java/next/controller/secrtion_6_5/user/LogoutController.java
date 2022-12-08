package next.controller.secrtion_6_5.user;

import core.mvc.section_6_5.Controller;
import core.mvc.section_8_3.JspView;
import core.mvc.section_8_3.ModelAndView;
import core.mvc.section_8_3.View;
import next.controller.secrtion_6_5.UserSessionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutController implements Controller {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        HttpSession session = req.getSession();
        session.removeAttribute(UserSessionUtils.USER_SESSION_KEY);
        return new ModelAndView(new JspView( "redirect:/"));
    }
}
