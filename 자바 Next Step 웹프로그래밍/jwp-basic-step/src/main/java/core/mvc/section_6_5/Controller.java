package core.mvc.section_6_5;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller {
    
    String execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
