package com.object.book.chapter_4.part_total;


import com.object.book.chapter_2.part_total.Money;

public class ReservationAgency {

    public Reservation reserveV2(Screening screening, Customer customer, int audienceCount){
        Money fee = screening.calculateFee(audienceCount);
        return new Reservation(customer, screening, fee, audienceCount);
    }

    public Reservation reserveV1(Screening screening, Customer customer, int audienceCount){
        Movie movie = screening.getMovie();

        boolean discountable = false;

        for (DiscountCondition condition : movie.getDiscountConditions()) {
            if(condition.getType() == DiscountConditionType.PERIOD){
                discountable = screening
                        .getWhenScreened().getDayOfWeek().equals(condition.getDayOfWeek()) &&
                        condition.getWhenScreened().compareTo(screening.getWhenScreened().toLocalTime()) <= 0 &&
                        condition.getEndTime().compareTo(screening.getWhenScreened().toLocalTime()) >= 0;
            }
            else {
                discountable = condition.getSequence() == screening.getSequence();
            }
            if (discountable){
                break;
            }
        }

        Money fee;

        if(discountable){
            Money discountAmount = Money.ZERO;
            switch(movie.getMovieType()){
                case AMOUNT_DISCOUNT:
                    discountAmount = movie.getDiscountAmount();
                    break;
                case PERCENT_DISCOUNT:
                    discountAmount = movie.getFee().times(movie.getDiscountPercent());
                    break;
                case NONE_DISCOUNT:
                    discountAmount = Money.ZERO;
                    break;
            }
            fee = movie.getFee().minus(discountAmount);
        }
        else{
            fee = movie.getFee();
        }
        return new Reservation(customer, screening, fee, audienceCount);
    }
}

/**
 * 어떤 객체를 수정해도 ReservationAgency는 모든 의존성의 결합지이므로 이 클래스의 변경을 유발한다.
 */