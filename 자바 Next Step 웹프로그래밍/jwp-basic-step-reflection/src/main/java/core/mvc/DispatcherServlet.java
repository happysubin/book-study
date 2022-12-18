package core.mvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;
import core.nmvc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private List<HandlerAdapter> adapters = Lists.newArrayList();
    private List<HandlerMapping> rm = new ArrayList<>();

    @Override
    public void init() throws ServletException {

        LegacyHandlerMapping legacyHandlerMapping = new LegacyHandlerMapping();
        legacyHandlerMapping.initMapping();;

        AnnotationHandlerMapping annotationHandlerMapping = new AnnotationHandlerMapping("next.controller");
        annotationHandlerMapping.initialize();

        rm.add(legacyHandlerMapping);
        rm.add(annotationHandlerMapping);

        adapters.add(new ControllerHandlerAdapter());
        adapters.add(new HandlerExecutionHandlerAdapter());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        Object handler = getHandler(req);
        try {
            ModelAndView mav = execute(req, resp, handler);
            View view = mav.getView();
            view.render(mav.getModel(), req, resp);
        } catch (Throwable e) {
            logger.error("Exception : {}", e);
            throw new ServletException(e.getMessage());
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

    private ModelAndView execute(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        for (HandlerAdapter adapter : adapters) {
            if(adapter.supports(handler)){
                return adapter.handle(req, resp, handler);
            }
        }
        return null;
    }
}
