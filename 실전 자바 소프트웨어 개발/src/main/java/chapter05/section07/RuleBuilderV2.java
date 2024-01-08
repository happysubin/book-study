package chapter05.section07;

import chapter05.Action;

public class RuleBuilderV2 {

    private Condition condition;

    private RuleBuilderV2(Condition condition) {
        this.condition = condition;
    }

    public static RuleBuilderV2 when(final Condition condition) {
        return new RuleBuilderV2(condition);
    }

    public Rule then(Action action) {
        return new DefaultRule(condition, action);
    }
}
