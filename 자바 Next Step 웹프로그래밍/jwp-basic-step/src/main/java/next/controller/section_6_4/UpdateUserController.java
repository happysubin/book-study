package next.controller.section_6_4;

import core.db.DataBase;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class UpdateUserController implements Controller {

    private static final Logger log = LoggerFactory.getLogger(UpdateUserController.class);


    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String userId = request.getParameter("userId");
        HttpSession session = request.getSession();
        Object value = session.getAttribute("user");
        User user = DataBase.findUserById(userId);

        if(value != null){
            if(user == (User) value){
                user.update(
                        request.getParameter("password"),
                        request.getParameter("name"),
                        request.getParameter("email")
                );
                log.debug("user : {}", user);
                return "redirect:/user/list";
            }

            else{
                return "redirect:/";
            }
        }

        else{
            return "redirect:/";
        }
    }
}
