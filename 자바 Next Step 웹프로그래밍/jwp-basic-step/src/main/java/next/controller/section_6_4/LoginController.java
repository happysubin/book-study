package next.controller.section_6_4;

import core.db.DataBase;
import next.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class LoginController implements Controller {


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userId = request.getParameter("userId");
        String password = request.getParameter("password");
        User user = DataBase.findUserById(userId);
        if(user.login(password)){
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
        }

        return "redirect:/";
    }
}
