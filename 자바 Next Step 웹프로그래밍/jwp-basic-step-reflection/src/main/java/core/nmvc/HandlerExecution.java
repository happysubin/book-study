package core.nmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import core.mvc.ModelAndView;

import java.lang.reflect.Method;

public class HandlerExecution {

    private Method method;
    private Object declaredObject;

    public HandlerExecution(Method method, Object declaredObject) {
        this.method = method;
        this.declaredObject = declaredObject;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response){
        try{
            return (ModelAndView) method.invoke(declaredObject, request, response);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
