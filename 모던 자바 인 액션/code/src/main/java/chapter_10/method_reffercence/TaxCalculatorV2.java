package chapter_10.method_reffercence;

import chapter_10.Order;

import java.util.function.DoubleUnaryOperator;

public class TaxCalculatorV2 {

    private DoubleUnaryOperator taxFunction = d -> d;

    public TaxCalculatorV2 with(DoubleUnaryOperator f){
        taxFunction = taxFunction.andThen(f);
        return this;
    }

    public double calculate(Order order){
        return taxFunction.applyAsDouble(order.getValue());
    }

    public static void main(String[] args) {
        Order order = new Order();
        double calculate = new TaxCalculatorV2()
                .with(Tax::regional)
                .with(Tax::surcharge)
                .calculate(order);
    }

}
