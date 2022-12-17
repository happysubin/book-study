package core.mvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.nmvc.AnnotationHandlerMapping;
import core.nmvc.HandlerExecution;
import core.nmvc.HandlerMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private List<HandlerMapping> rm = new ArrayList<>();

    @Override
    public void init() throws ServletException {

        LegacyHandlerMapping legacyHandlerMapping = new LegacyHandlerMapping();
        legacyHandlerMapping.initMapping();;

        AnnotationHandlerMapping annotationHandlerMapping = new AnnotationHandlerMapping("next.controller");
        annotationHandlerMapping.initialize();

        rm.add(legacyHandlerMapping);
        rm.add(annotationHandlerMapping);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        Object handler = getHandler(req);
        try {
            execute(req, resp, handler);
        } catch (Throwable e) {
            logger.error("Exception : {}", e);
            throw new ServletException(e.getMessage());
        }
    }

    private void execute(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        ModelAndView mav;
        if(handler instanceof HandlerExecution){
            HandlerExecution handlerExecution = (HandlerExecution) handler;
            mav = handlerExecution.handle(req, resp);
            View view = mav.getView();
            view.render(mav.getModel(), req, resp);
        }

        else if( handler instanceof Controller){
            Controller controller = (Controller) handler;
            mav = controller.execute(req, resp);
            View view = mav.getView();
            view.render(mav.getModel(), req, resp);
        }
        else{
            throw new RuntimeException("지원하는 컨트롤러, 핸들러가 아닙니다");
        }
    }

    private Object getHandler(HttpServletRequest request) {
        for (HandlerMapping handlerMapping : rm) {
            Object handler = handlerMapping.getHandler(request);
            if(handler != null){
                return handler;
            }
        }
        return null;
    }
}
