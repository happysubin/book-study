package core.ref;

import core.nmvc.AnnotationHandlerMapping;
import core.nmvc.ControllerScanner;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.model.Question;
import next.model.User;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class ReflectionTest {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;

        Arrays.stream(clazz.getDeclaredFields())
                        .forEach(System.out::println);

        Arrays.stream(clazz.getDeclaredConstructors())
                        .forEach(System.out::println);

        Arrays.stream(clazz.getDeclaredMethods())
                        .forEach(System.out::println);

        //logger.debug(clazz.getName());
    }
    
    @Test
    public void newInstanceWithConstructorArgs() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<User> clazz = User.class;
        User user = clazz
                .getDeclaredConstructor(String.class, String.class, String.class, String.class)
                .newInstance("userId", "password", "name", "email");
        System.out.println("user = " + user);
    }
    
    @Test
    public void privateFieldAccess() throws NoSuchFieldException, IllegalAccessException {
        Class<Student> clazz = Student.class;
        Student student = new Student();

        Field f1 = clazz.getDeclaredField("name");
        f1.setAccessible(true);
        f1.set(student, "subin");

        Field f2 = clazz.getDeclaredField("age");
        f2.setAccessible(true);
        f2.set(student, 9999);


        System.out.println("student = " + student);
        logger.debug(clazz.getName());
    }

    @Test
    public void controllerAnnotationTest() throws Exception{
        ControllerScanner.initRequestMapping();
        AnnotationHandlerMapping annotationHandlerMapping = new AnnotationHandlerMapping();
        annotationHandlerMapping.initialize();
    }
}
