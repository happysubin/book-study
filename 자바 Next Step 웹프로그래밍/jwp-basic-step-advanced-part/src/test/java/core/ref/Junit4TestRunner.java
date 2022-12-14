package core.ref;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class Junit4TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit4Test> clazz = Junit4Test.class;

        Arrays.stream(clazz.getDeclaredMethods())
                .forEach(m -> {
                    if(m.isAnnotationPresent(MyTest.class)){
                        try {
                            Junit4Test junit4Test = clazz.getDeclaredConstructor().newInstance();
                            m.invoke(junit4Test);
                        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
