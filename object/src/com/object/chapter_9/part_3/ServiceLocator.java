package com.object.chapter_9.part_3;

import com.object.chapter_2.part_total.discountpolicy.DiscountPolicy;

public class ServiceLocator {

    private static ServiceLocator soleInstance = new ServiceLocator();
    private static DiscountPolicy discountPolicy;

    public static DiscountPolicy discountPolicy(){
        return soleInstance.discountPolicy;
    }

    public static void provide(DiscountPolicy discountPolicy){
        soleInstance.discountPolicy = discountPolicy;
    }

    private ServiceLocator(){

    }
}
