package chapter05;

import chapter05.section06.ConditionalAction;
import chapter05.section06.Inspector;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InspectorTest {

    @Test
    void inspectOneConditionEvaluatesTrue() {
        Facts facts = new Facts();
        facts.addFact("jobTitle", "CEO");
        JobTitleCondition condition = new JobTitleCondition();

        Inspector inspector = new Inspector(condition);

        List<Report> result = inspector.inspect(facts);

        assertEquals(1, result.size());

    }


    static class JobTitleCondition implements ConditionalAction {

        @Override
        public boolean evaluate(Facts facts) {
            return "CEO".equals(facts.getFact("jobTitle"));
        }

        @Override
        public void perform(Facts facts) {
            throw new UnsupportedOperationException("");
        }
    }
}