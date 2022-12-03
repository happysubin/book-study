package chapter_2.section_2.quiz;

import chapter_2.section_2.ApplePredicate;
import chapter_2.setting.Apple;

public class AppleFancyPrintPredicate implements ApplePrintPredicate {

    @Override
    public String print(Apple apple) {
        String chars = apple.getWeight() > 150 ? "heavy" : "light";
        return "A " + chars + " " + apple.getColor().toString() + " apple";

    }
}
