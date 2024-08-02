package com.object.book.chapter_12.part_total;

import java.util.Arrays;

public class Application {

    public static void main(String[] args) {
        Lecture lecture = new Lecture(70, "객체지향 프로그래밍 ", Arrays.asList(81, 95, 75, 60, 54));
        String evaluate = lecture.evaluate();
        System.out.println("evaluate = " + evaluate);


        Professor professor = new Professor("다익스트라",
                new Lecture(70,   //GradeLecture 클래스 인스턴스가 들어가도 문제가 없다.
                        "알고리즘",
                        Arrays.asList(81, 96, 75, 54, 45)));
        System.out.println("professor = " + professor.compileStatistics());

    }
}
