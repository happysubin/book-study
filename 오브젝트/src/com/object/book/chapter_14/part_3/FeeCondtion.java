package com.object.book.chapter_14.part_3;

import com.object.book.chapter_14.part_1.Call;
import com.object.book.chapter_14.part_1.DateTimeInterval;

import java.util.List;

public interface FeeCondtion {

    List<DateTimeInterval> findTimeIntervals(Call call);

}
