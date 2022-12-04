package next.controller.section_6_4;

import core.db.DataBase;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class UpdateUserFormController implements Controller {


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userId = request.getParameter("userId");
        request.setAttribute("user", DataBase.findUserById(userId));
        RequestDispatcher rd = request.getRequestDispatcher("/user/update.jsp");

        return "/user/update.jsp";
    }
}
