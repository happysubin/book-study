package com.object.chapter_12.part_total;

import java.util.List;

import static java.util.stream.Collectors.joining;

public class GradeLecture extends Lecture{

    private List<Grade> grades;

    public GradeLecture(int pass, String title, List<Integer> scores, List<Grade> grades) {
        super(pass, title, scores);
        this.grades = grades;
    }

    @Override
    public String evaluate() {
        return super.evaluate() +", " + gradesStatistics();
    }

    private String gradesStatistics() {
        return grades.stream()
                .map(grade -> format(grade))
                .collect(joining(" "));
    }

    private String format(Grade grade){
        return String.format("%s:$d", grade.getName(), gradeCount(grade));
    }

    private long gradeCount(Grade grade){
        return getScores().stream()
                .filter(grade::include)
                .count();
    }

}
