package chapter_9;

public class StrategyApplication {

    public static void main(String[] args) {

        StrategyApplication strategyApplication = new StrategyApplication();
        strategyApplication.execute();
        System.out.println();
        strategyApplication.executeV2();
    }

    private void executeV2() {

        Validator numericValidator = new Validator((s) -> s.matches("\\d+"));
        boolean b1 = numericValidator.validate("bbb");
        System.out.println("b1 = " + b1);

        Validator lowerCaseValidator = new Validator((s) -> s.matches("[a-z]+"));
        boolean b2 = lowerCaseValidator.validate("bbb");
        System.out.println("b2 = " + b2);
    }

    private void execute() {

        Validator numericValidator = new Validator(new IsNumeric());
        boolean b1 = numericValidator.validate("aaaa");
        System.out.println("b1 = " + b1);

        Validator lowerCaseValidator = new Validator(new IsAllowLowerCase());
        boolean b2 = lowerCaseValidator.validate("bbb");
        System.out.println("b2 = " + b2);
    }


    class Validator{
        private final ValidationStrategy strategy;

        public Validator(ValidationStrategy strategy) {
            this.strategy = strategy;
        }

        public boolean validate(String s){
            return strategy.execute(s);
        }
    }

    interface ValidationStrategy{
        boolean execute(String s);
    }

    class IsAllowLowerCase implements ValidationStrategy{

        @Override
        public boolean execute(String s) {
            return s.matches("[a-z]+");
        }
    }

    class IsNumeric implements ValidationStrategy{

        @Override
        public boolean execute(String s) {
            return s.matches("\\d+");
        }
    }
}
