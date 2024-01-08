package chapter05;

import chapter05.section07.Condition;
import chapter05.section07.DefaultRule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DefaultRuleTest {


    @Test
    void test() {
        Condition condition = (Facts facts) -> "CEO".equals(facts.getFact("jobTitle"));

        Action action = (Facts facts) -> {
            String name = facts.getFact("name");
            System.out.println(name);
            //TODO 메일 보내는 로직
        };

        DefaultRule rule = new DefaultRule(condition, action);

    }

}