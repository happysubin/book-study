package tobyspring.vol1.callbacktemplate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

public class CalSumTest {

    Calculator calculator;
    String filePath;

    @BeforeEach
    void beforeEach(){
        this.calculator = new Calculator();
        this.filePath = "/Users/ansubin/Desktop/book-study/토비의 스프링/vol1/src/test/java/tobyspring/vol1/callbacktemplate/numbers.txt";
    }

    @Test
    void sumOfNumbers() throws IOException {
        assertThat(calculator.calSum(filePath)).isEqualTo(10);
    }

    @Test
    void multiplyOfNumbers() throws IOException {
        assertThat(calculator.calMultiply(filePath)).isEqualTo(24);
    }

    @Test
    void concatenate() throws IOException {
        assertThat(calculator.concatenate(filePath)).isEqualTo("1234");
    }
}
