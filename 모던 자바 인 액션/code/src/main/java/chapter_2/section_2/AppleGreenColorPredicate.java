package chapter_2.section_2;

import chapter_2.setting.Apple;

import static chapter_2.setting.Color.GREEN;

/**
 * 초록 사과만 선택
 */

public class AppleGreenColorPredicate implements ApplePredicate{
    @Override
    public boolean test(Apple apple)   {
        return GREEN.equals(apple.getColor());
    }
}
