package com.object.chapter_14.part_3;

import com.object.chapter_14.part_1.Call;
import com.object.chapter_14.part_1.DateTimeInterval;

import java.time.DayOfWeek;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DurationFeeCondition implements FeeCondtion{

    private Duration from;
    private Duration to;

    public DurationFeeCondition(Duration from, Duration to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public List<DateTimeInterval> findTimeIntervals(Call call) {

        if(call.getInterval().duration().compareTo(from) < 0){
            return Collections.emptyList();
        }

        return Arrays.asList(
                DateTimeInterval.of(
                        call.getInterval().getFrom().plus(from),
                        call.getInterval().duration().compareTo(to) > 0 ?
                                call.getInterval().getFrom().plus(to ) :
                                call.getInterval().getTo()));
    }
}
