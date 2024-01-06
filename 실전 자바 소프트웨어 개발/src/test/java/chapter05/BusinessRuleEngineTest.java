package chapter05;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BusinessRuleEngineTest {


    @Test
    void shouldHaveNoRulesInitially() {
        Facts mockFacts = mock(Facts.class);
        final BusinessRuleEngine businessRuleEngine = new BusinessRuleEngine(mockFacts);

        assertEquals(0, businessRuleEngine.count());
    }

    @Test
    void shouldAddTwoActions() {
        Facts mockFacts = mock(Facts.class);
        final BusinessRuleEngine businessRuleEngine = new BusinessRuleEngine(mockFacts);

        businessRuleEngine.addAction((facts) -> {});
        businessRuleEngine.addAction((facts) -> {});

        assertEquals(2, businessRuleEngine.count());

    }

    @Test
    void shouldExecuteOneAction() {
        Facts mockFacts = mock(Facts.class);
        final Action mockAction = mock(Action.class);
        final BusinessRuleEngine businessRuleEngine = new BusinessRuleEngine(mockFacts);

        businessRuleEngine.addAction(mockAction);
        businessRuleEngine.run();

        verify(mockAction, Mockito.times(1));
    }


    @Test
    void shouldPerformAnActionWithFacts() {
        Facts facts = new Facts();
        facts.addFact("jobTitle", "CEO");

        BusinessRuleEngine businessRuleEngine = new BusinessRuleEngine(facts);

        businessRuleEngine.addAction(f -> {
            String jobTitle = f.getFact("jobTitle");
            System.out.println(jobTitle);
            if("CEO".equals(jobTitle)) {
                var name = f.getFact("name");
                //sendEmail 로직
            }
        });

        businessRuleEngine.run();
    }
}