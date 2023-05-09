package com.code.code.chapter_3;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CalculatorTest {

    @Test
    void sum_of_two_numbers(){

        //given
        double first = 10;
        double second = 20;
        Calculator calculator = new Calculator();

        //when
        double sum = calculator.sum(first, second);

        //then
        Assertions.assertThat(sum).isEqualTo(30);
    }

}