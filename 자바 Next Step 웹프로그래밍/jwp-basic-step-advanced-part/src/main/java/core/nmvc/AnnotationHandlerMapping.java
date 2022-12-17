package core.nmvc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.google.common.collect.Maps;

import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import org.reflections.ReflectionUtils;

public class AnnotationHandlerMapping implements HandlerMapping{

    private String basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(String basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        ControllerScanner.initRequestMapping(basePackage);
        ControllerScanner
                .getControllers()
                .entrySet()
                .forEach(entry -> {
                    ReflectionUtils.getAllMethods(entry.getKey(), ReflectionUtils.withAnnotation(RequestMapping.class))
                            .forEach(method -> {
                                RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                                handlerExecutions.put(new HandlerKey(annotation.value(), annotation.method()), new HandlerExecution(method, entry.getValue()));
                            });
                });
    }

    @Override
    public HandlerExecution getHandler(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        RequestMethod rm = RequestMethod.valueOf(request.getMethod().toUpperCase());
        return handlerExecutions.get(new HandlerKey(requestUri, rm));
    }
}
