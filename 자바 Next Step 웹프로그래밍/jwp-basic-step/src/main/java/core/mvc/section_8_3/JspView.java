package core.mvc.section_8_3;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class JspView implements View{

    private String path;
    private static final String DEFAULT_REDIRECT_PREFIX = "redirect:";


    public JspView(String path) {
        this.path = path;
    }

    @Override
    public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

        if(path == null){
            throw new RuntimeException("요청하는 경로가 없습니다");
        }

        if(path.startsWith(DEFAULT_REDIRECT_PREFIX)){
            response.sendRedirect(path.substring(DEFAULT_REDIRECT_PREFIX.length()));
            return;
        }

        modelToRequestAttributes(model, request);

        RequestDispatcher rd = request.getRequestDispatcher(path);
        rd.forward(request, response);
    }

    private void modelToRequestAttributes(Map<String,?> model, HttpServletRequest request) {
        for (String key : model.keySet()) {
            request.setAttribute(key, model.get(key));
        }
    }
}
