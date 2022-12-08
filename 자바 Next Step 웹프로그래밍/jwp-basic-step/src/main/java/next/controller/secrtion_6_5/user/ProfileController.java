package next.controller.secrtion_6_5.user;

import core.db.DataBase;
import core.mvc.section_6_5.Controller;
import core.mvc.section_8_3.JspView;
import core.mvc.section_8_3.ModelAndView;
import core.mvc.section_8_3.View;
import next.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProfileController implements Controller {

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        String userId = req.getParameter("userId");
        User user = DataBase.findUserById(userId);
        if (user == null) {
            throw new NullPointerException("사용자를 찾을 수 없습니다.");
        }
        req.setAttribute("user", user);

        ModelAndView modelAndView = new ModelAndView(new JspView("/user/profile.jsp"));
        modelAndView.setModelData("user", user);
        return modelAndView;
    }
}
