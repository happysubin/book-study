package tobyspring.vol1;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JUnitTest {

    static JUnitTest test;

    @Test
    void test1(){
        assertThat(this).isNotSameAs(test);
        test = this;
    }

    @Test
    void test2(){
        assertThat(this).isNotSameAs(test);
        test = this;
    }

    @Test
    void test3(){
        assertThat(this).isNotSameAs(test);
        test = this;
    }
}
