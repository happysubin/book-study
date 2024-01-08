package chapter05.section07;

import chapter05.Action;

public class RuleBuilderV1 {

    private Condition condition;
    private Action action;

    public RuleBuilderV1 when(final Condition condition) {
        this.condition = condition;
        return this;
    }

    public RuleBuilderV1 then(final Action action) {
        this.action = action;
        return this;
    }

    public Rule createRule() {
        return new DefaultRule(condition, action);
    }
}
