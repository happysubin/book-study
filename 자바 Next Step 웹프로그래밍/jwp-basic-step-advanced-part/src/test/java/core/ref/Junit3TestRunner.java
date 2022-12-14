package core.ref;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class Junit3TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit3Test> clazz = Junit3Test.class;
        Arrays.stream(clazz.getDeclaredMethods())
                .forEach(m -> {
                    if(m.getName().startsWith("test")){
                        try {
                            Junit3Test junit3Test = clazz.getDeclaredConstructor().newInstance();
                            m.invoke(junit3Test);
                        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
