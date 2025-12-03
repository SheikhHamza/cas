package com.multibank.assignment.cas;


import com.multibank.assignment.cas.Util.IntervalUtil;
import com.multibank.assignment.cas.model.BidAskEvent;
import com.multibank.assignment.cas.model.Candle;
import com.multibank.assignment.cas.model.Interval;
import com.multibank.assignment.cas.service.CandleAggregatorService;
import com.multibank.assignment.cas.service.CandleStorageService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class CandleAggregatorServiceTest {


    @Test
    void basicAggregationTest() throws InterruptedException {
        CandleStorageService storage = new CandleStorageService();
        CandleAggregatorService agg = new CandleAggregatorService(storage);


        long now = System.currentTimeMillis();
        BidAskEvent e1 = new BidAskEvent("BTC-USD", 100.0, 101.0, now);
        agg.accept(e1);


        BidAskEvent e2 = new BidAskEvent("BTC-USD", 102.0, 103.0, now + 200);
        agg.accept(e2);


        long bucketFrom = IntervalUtil.timestampBucketFromInterval (Interval.S1, now);
        long bucketTo = IntervalUtil.timestampBucketFromInterval(Interval.S1, now + 200);
        var opt = storage.range("BTC-USD", Interval.S1, bucketFrom, bucketTo);

        assertFalse(opt.isEmpty());
        Candle c = opt.get(0);
        assertEquals((100.0 + 101.0) / 2.0, c.open());
        assertEquals((102.0 + 103.0) / 2.0, c.close());
        assertEquals(2, c.volume());
    }
}