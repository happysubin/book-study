package com.object.chapter_4.part_total;

import com.object.chapter_2.part_total.Money;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class Movie {

    private String title;
    private Duration runningTime;
    private Money fee;
    private List<DiscountCondition> discountConditions;
    private MovieType movieType;
    private Money discountAmount;
    private double discountPercent;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Duration getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(Duration runningTime) {
        this.runningTime = runningTime;
    }

    public Money getFee() {
        return fee;
    }

    public void setFee(Money fee) {
        this.fee = fee;
    }

    public List<DiscountCondition> getDiscountConditions() {
        return discountConditions;
    }

    public void setDiscountConditions(List<DiscountCondition> discountConditions) {
        this.discountConditions = discountConditions;
    }

    public MovieType getMovieType() {
        return movieType;
    }

    public void setMovieType(MovieType movieType) {
        this.movieType = movieType;
    }

    public Money getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Money discountAmount) {
        this.discountAmount = discountAmount;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public Money calculateAmountDiscountedFee(){
        if(movieType != MovieType.AMOUNT_DISCOUNT){
            throw new IllegalArgumentException();
        }
        return fee.minus(discountAmount);
    }

    public Money calculatePercentDiscountedFee(){
        if(movieType != MovieType.PERCENT_DISCOUNT){
            throw new IllegalArgumentException();
        }
        return fee.minus(fee.times(discountPercent));
    }

    public Money calculateNoneDiscountedFee(){
        if(movieType != MovieType.NONE_DISCOUNT){
            throw new IllegalArgumentException();
        }
        return fee;
    }

    public boolean isDiscountable(LocalDateTime whenScreened, int sequence){
        for (DiscountCondition condition : discountConditions) {
            if(condition.getType() == DiscountConditionType.PERIOD){
                if(condition.isDiscountable(whenScreened.getDayOfWeek(), whenScreened.toLocalTime())){
                    return true;
                }
            }
            else{
                if(condition.isDiscountable(sequence)){
                    return true;
                }
            }
        }
        return false;
    }
}

/**
 * 접근자와 수정자 메서드는 객체 내부의 상태에 대한 어떤 정보도 캡슐화하지 못함.
 * getFee, setFee 메서드는 Movie 내부에 Money Movie 타입의 fee라는 이름의 인스턴스 변수가 존재한다는 사실을 퍼블릭 인터페이스에 노골적으로 드러낸다.
 *
 */