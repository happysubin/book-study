package chapter_10.method_reffercence;

import chapter_10.Order;

public class Tax {

    public static double regional(double value){
        return value * 1.1;
    }

    public static double general(double value){
        return value * 1.3;
    }

    public static double surcharge(double value){
        return value * 1.05;
    }

    public static void main(String[] args) {
        Order order = new Order();
        calculate(order, true, false, true);
    }

    public static double calculate(Order order, boolean userRegional, boolean userGeneral, boolean userSurcharge){
        double value = order.getValue();
        if(userRegional) value = Tax.regional(value);
        if(userGeneral) value = Tax.general(value);
        if(userSurcharge) value =Tax.surcharge(value);
        return value;
    }
}
