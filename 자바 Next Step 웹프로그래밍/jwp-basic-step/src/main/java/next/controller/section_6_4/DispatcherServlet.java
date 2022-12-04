package next.controller.section_6_4;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 0) //loadOnStartUp 초기화 우선 순.
public class DispatcherServlet extends HttpServlet {

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try{
            String requestURI = req.getRequestURI(); //URI가 우리가 원하는 /user/create 이렇게 뽑아준다.
            Controller controller = RequestMapping.getController(requestURI);
            String view = controller.execute(req, res);
            ViewResolver.service(view, req, res);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
