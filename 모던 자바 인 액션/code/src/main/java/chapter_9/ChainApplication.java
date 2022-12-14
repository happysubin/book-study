package chapter_9;


import java.util.function.Function;
import java.util.function.UnaryOperator;

public class ChainApplication {

    public static void main(String[] args) {
        ChainApplication chainApplication = new ChainApplication();
        chainApplication.execute();
        chainApplication.executeWithLambda();
    }

    private void execute() {

        HeaderTextProcessing p1 = new HeaderTextProcessing();
        SpellCheckingProcessing p2 = new SpellCheckingProcessing();
        p1.setSuccessor(p2); //두 작업 처리 객체를 연결
        String result = p1.handle("Aren't labdas really sexy?!?!?");
        System.out.println("result = " + result);
    }

    private void executeWithLambda(){

        UnaryOperator<String> headerProcessing = (String text) -> "From Raoul, Mario and Alan: " + text;
        UnaryOperator<String> spellCheckerProcessing = (String text) -> text.replaceAll("labda", "lambda");

        Function<String, String> pipeLine = headerProcessing.andThen(spellCheckerProcessing);
        String result = pipeLine.apply("Arent't labdas really sexy?!!");
        System.out.println("result = " + result);

    }


    abstract class ProcessingObject<T>{

        protected ProcessingObject<T> successor;

        public void setSuccessor(ProcessingObject<T> successor){
            this.successor = successor;
        }

        public T handle (T input){

            T r = handleWork(input);

            if(successor != null){
                return successor.handle(r);
            }
            return r;
        }

        abstract protected T handleWork(T input);
    }

    class HeaderTextProcessing extends ProcessingObject<String>{

        @Override
        protected String handleWork(String text) {
            return "From Raoul, Mario and Alan:: " + text;
        }
    }

    class SpellCheckingProcessing extends ProcessingObject<String>{

        @Override
        protected String handleWork(String text) {
            return text.replaceAll("labda", "lambda");
        }
    }
}
