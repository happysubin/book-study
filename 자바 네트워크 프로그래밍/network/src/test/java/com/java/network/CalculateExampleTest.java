package com.java.network;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class CalculateExampleTest {

    @Test
    void test() throws NoSuchMethodException {
        //System.out.println(12.1 + 0.1 == 1.2);

        Method print1 = Data.class.getMethod("print", String.class);
        Method print2 = Data.class.getMethod("print", String.class);


        System.out.println("print1 = " + print1.hashCode());
        System.out.println("print2 = " + print2.hashCode());

    }

    static class Data {
        public void print(String name) {
            System.out.println("hi");
        }
    }
}
