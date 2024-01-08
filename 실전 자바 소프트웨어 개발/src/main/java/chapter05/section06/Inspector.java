package chapter05.section06;

import chapter05.Facts;
import chapter05.Report;
import chapter05.section06.ConditionalAction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Inspector {

    private final List<ConditionalAction> conditionalActions;

    public Inspector(ConditionalAction... conditionalActions) {
        this.conditionalActions = Arrays.asList(conditionalActions);
    }

    public List<Report> inspect(final Facts facts) {
        final List<Report> reports = new ArrayList<>();
        for (ConditionalAction conditionalAction : conditionalActions) {
            final boolean conditionResult = conditionalAction.evaluate(facts);
            reports.add(new Report(facts, conditionalAction, conditionResult));
        }
        return reports;
    }
}
