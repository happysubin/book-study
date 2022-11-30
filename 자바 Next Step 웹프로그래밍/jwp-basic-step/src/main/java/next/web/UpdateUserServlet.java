package next.web;

import core.db.DataBase;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/users/update")
public class UpdateUserServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(UpdateUserServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userId = req.getParameter("userId");
        HttpSession session = req.getSession();
        Object value = session.getAttribute("user");
        User user = DataBase.findUserById(userId);

        if(value != null){
            if(user == (User) value){
                user.update(
                        req.getParameter("password"),
                        req.getParameter("name"),
                        req.getParameter("email")
                );
                log.debug("user : {}", user);
                resp.sendRedirect("/users/list");
            }

            else{
                resp.sendRedirect("/");
            }
        }

        else{
            resp.sendRedirect("/");
        }
    }
}
