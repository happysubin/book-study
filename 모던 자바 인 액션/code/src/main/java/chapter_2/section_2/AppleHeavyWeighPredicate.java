package chapter_2.section_2;

import chapter_2.setting.Apple;

/**
 * 무거운 사과만 선택
 */

public class AppleHeavyWeighPredicate implements ApplePredicate{
    @Override
    public boolean test(Apple apple)   {
        return apple.getWeight() > 150;


    }
}
