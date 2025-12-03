package com.multibank.assignment.cas.Util;

import com.multibank.assignment.cas.model.Interval;

public class IntervalUtil {

    public static long timestampBucketFromInterval(Interval interval, long timeStamp){
        return (timeStamp / interval.millis()) * interval.millis();
    }
}
