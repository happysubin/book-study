package chapter05;

import chapter05.section06.ConditionalAction;

public class Report {

    private final Facts facts;
    private final ConditionalAction conditionalAction;
    private final boolean isPositive;

    public Report(Facts facts, ConditionalAction conditionalAction, boolean isPositive) {
        this.facts = facts;
        this.conditionalAction = conditionalAction;
        this.isPositive = isPositive;
    }

    public Facts getFacts() {
        return facts;
    }

    public ConditionalAction getConditionalAction() {
        return conditionalAction;
    }

    public boolean isPositive() {
        return isPositive;
    }

    @Override
    public String toString() {
        return "Report{" +
                "facts=" + facts +
                ", conditionalAction=" + conditionalAction +
                ", isPositive=" + isPositive +
                '}';
    }
}


