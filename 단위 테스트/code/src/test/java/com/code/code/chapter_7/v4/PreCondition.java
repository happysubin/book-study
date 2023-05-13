package com.code.code.chapter_7.v4;

public class PreCondition {
    public static void requires(boolean precondition, String message){
        if (!precondition) {
            throw new RuntimeException(message);
        }
    }

    public static void requires(boolean precondition) {
        requires(precondition, null);
    }
}
