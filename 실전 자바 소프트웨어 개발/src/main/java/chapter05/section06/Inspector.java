package chapter05;

import chapter04.ReportImporter;

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
