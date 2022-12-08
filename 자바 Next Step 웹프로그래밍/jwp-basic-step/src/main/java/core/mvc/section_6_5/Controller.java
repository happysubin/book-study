package core.mvc.section_6_5;

import core.mvc.section_8_3.ModelAndView;
import core.mvc.section_8_3.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Controller {
    
    ModelAndView execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
