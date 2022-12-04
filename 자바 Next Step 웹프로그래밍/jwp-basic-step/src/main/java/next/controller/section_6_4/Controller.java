package next.controller.section_6_4;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller {

    String execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
