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

    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        ControllerScanner.initRequestMapping();
        ControllerScanner
                .getControllers()
                .entrySet()
                .forEach(entry -> {
                    Set<Method> methods = ReflectionUtils.getAllMethods(entry.getKey(), ReflectionUtils.withAnnotation(RequestMapping.class));
                    methods
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
