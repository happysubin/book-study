package chapter05;

import chapter05.section07.BusinessRuleEngine;
import chapter05.section07.Rule;
import chapter05.section07.RuleBuilderV2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdvancedBusinessRuleEngineTest {



    @Test
    void test() {

        Facts myFacts = new Facts();
        myFacts.addFact("name", "su");
        myFacts.addFact("jobTitle", "CEO");


        BusinessRuleEngine businessRuleEngine = new BusinessRuleEngine(myFacts);

        Rule rule = RuleBuilderV2
                .when(facts -> "CEO".equals(facts.getFact("jobTitle")))
                .then(facts -> {
                    String name = facts.getFact("name");
                    System.out.println(name);
                    //메일 보내기 기능이 있다고 가정
                });

        businessRuleEngine.addRule(rule);

        businessRuleEngine.run();
    }

}