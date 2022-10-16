package com.object.chapter_14.part_3;

import com.object.chapter_14.part_1.Call;
import com.object.chapter_14.part_1.DateTimeInterval;

import java.util.Arrays;
import java.util.List;

public class FixedFeeCondition implements FeeCondtion{


    @Override
    public List<DateTimeInterval> findTimeIntervals(Call call) {
        return Arrays.asList(call.getInterval());
    }
}
