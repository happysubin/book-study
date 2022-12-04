package next.controller.section_6_4;

import core.db.DataBase;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ListUserController implements Controller{

    private static final long serialVersionUID = 1L;

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("users", DataBase.findAll());
        RequestDispatcher rd = request.getRequestDispatcher("/user/list.jsp");
        return "/user/list.jsp";
    }
}
