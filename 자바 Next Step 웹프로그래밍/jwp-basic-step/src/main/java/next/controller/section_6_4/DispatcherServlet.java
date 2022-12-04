package next.controller.section_6_4;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 0) //loadOnStartUp 초기화 우선 순.
public class DispatcherServlet extends HttpServlet {

    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        try{
            StringBuffer url = req.getRequestURL();
            String path = parseUrlToPath(String.valueOf(url));
            System.out.println("path = " + path);
            Controller controller = RequestMapping.getController(path);
            String view = controller.execute(req, res);

            ViewResolver.service(view, req, res);

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private String parseUrlToPath(String url) {
        url = url.split("http://localhost:8080")[1];
        return url.split("\\?")[0];
    }
}
