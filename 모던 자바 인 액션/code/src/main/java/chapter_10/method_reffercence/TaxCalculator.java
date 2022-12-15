package chapter_10.method_reffercence;

import chapter_10.Order;

public class TaxCalculator {

    private boolean useRegional;
    private boolean useGeneral;
    private boolean useSurcharge;

    public TaxCalculator withTaxRegional(){
        useRegional = true;
        return this;
    }

    public TaxCalculator withTaxGeneral(){
        useGeneral = true;
        return this;
    }

    public TaxCalculator withTaxSurcharge(){
        useSurcharge = true;
        return this;
    }

    public double calculate(Order order){
        return calculate(order, useRegional, useGeneral, useSurcharge);
    }

    public double calculate(Order order, boolean userRegional, boolean userGeneral, boolean userSurcharge){
        double value = order.getValue();
        if(userRegional) value = Tax.regional(value);
        if(userGeneral) value = Tax.general(value);
        if(userSurcharge) value =Tax.surcharge(value);
        return value;
    }

    public static void main(String[] args) {
        Order order = new Order();
        double calculate = new TaxCalculator()
                .withTaxGeneral()
                .withTaxSurcharge()
                .calculate(order);
    }

}
