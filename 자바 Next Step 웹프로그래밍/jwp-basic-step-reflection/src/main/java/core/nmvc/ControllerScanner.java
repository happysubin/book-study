package core.nmvc;


import core.annotation.Controller;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ControllerScanner {
    
    private static Map<Class<?>, Object > map = new HashMap<>();

    public static Map<Class<?>, Object > initRequestMapping(String basePackage) {
        Reflections reflections = new Reflections(basePackage);
        putControllerWithAnnotationInMap(reflections);
        return map;
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

    public static Map<Class<?>, Object >getControllers() {
        return map;
    }
}
