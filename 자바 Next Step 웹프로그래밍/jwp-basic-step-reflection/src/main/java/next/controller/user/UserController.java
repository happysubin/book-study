package next.controller.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import core.nmvc.AbstractNewController;
import next.controller.UserSessionUtils;
import next.dao.UserDao;
import core.mvc.ModelAndView;
import next.model.User;

@Controller
public class UserController extends AbstractNewController {

    private UserDao userDao = UserDao.getInstance();

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView getUsers(HttpServletRequest request, HttpServletResponse response) throws Exception {

        if (!UserSessionUtils.isLogined(request.getSession())) {
            return jspView("redirect:/users/loginForm");
        }

        ModelAndView mav = jspView("/user/list.jsp");
        mav.addObject("users", userDao.findAll());
        return mav;
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ModelAndView createUser(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = new User(request.getParameter("userId"), request.getParameter("password"),
                request.getParameter("name"), request.getParameter("email"));
        userDao.insert(user);
        return jspView("redirect:/");
    }

    @RequestMapping(value = "/users/update", method = RequestMethod.POST)
    public ModelAndView updateUser(HttpServletRequest req, HttpServletResponse response) throws Exception {
        User user = userDao.findByUserId(req.getParameter("userId"));

        if (!UserSessionUtils.isSameUser(req.getSession(), user)) {
            throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }

        System.out.println("user = " + user);
        User updateUser = new User(req.getParameter("userId"), req.getParameter("password"), req.getParameter("name"),
                req.getParameter("email"));
        user.update(updateUser);
        return jspView("redirect:/");
    }
}
