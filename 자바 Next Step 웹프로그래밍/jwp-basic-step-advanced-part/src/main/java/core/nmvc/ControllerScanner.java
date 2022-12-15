package core.nmvc;

import core.annotation.Controller;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ControllerScanner {
    
    public static Map<Class<?>, Object > map = new HashMap<>();

    public static void initRequestMapping(){

        Reflections reflections = new Reflections("next.controller");
        putControllerWithAnnotationInMap(reflections);
        ReflectionUtils.getAllMethods();

    }

    private static void putControllerWithAnnotationInMap(Reflections reflections) {
        reflections
                .getTypesAnnotatedWith(Controller.class)
                .forEach(aClass -> {
                    try {
                        map.put(aClass, aClass.getDeclaredConstructor().newInstance());
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                });
    }
}
