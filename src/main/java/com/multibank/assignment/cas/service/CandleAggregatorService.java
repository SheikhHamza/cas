package com.multibank.assignment.cas.service;


import com.multibank.assignment.cas.model.BidAskEvent;
import com.multibank.assignment.cas.model.Candle;
import com.multibank.assignment.cas.model.Interval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


@Service
public class CandleAggregatorService {


    private static final Logger log = LoggerFactory.getLogger(CandleAggregatorService.class);
    private final CandleStorageService storage;

    private final ConcurrentMap<String, Candle> partials = new ConcurrentHashMap<>();


    public CandleAggregatorService(CandleStorageService storage) {
        this.storage = storage;
    }

    private String key(String symbol, Interval interval, long bucket) {
        return symbol + '|' + interval.name() + '|' + bucket;
    }


    public void accept(BidAskEvent e) {
        for (Interval interval : Interval.values()) {
            acceptForInterval(e, interval);
        }
    }


    private void acceptForInterval(BidAskEvent e, Interval interval) {
        long bucket = (e.timestamp() / interval.millis()) * interval.millis();
        String k = key(e.symbol(), interval, bucket);

        log.info("Consuming bidAskEvent for interval {} with event data : {}", interval, e);

        partials.compute(k, (kk, existing) -> {
            if (existing == null) {
                double price = mid(e.bid(), e.ask());
                Candle c = new Candle(bucket, price, price, price, price, 1);
                storage.put(e.symbol(), interval, bucket, c);
                return c;
            } else {
                double price = mid(e.bid(), e.ask());
                double high = Math.max(existing.high(), price);
                double low = Math.min(existing.low(), price);
                double open = existing.open();
                double close = price;
                long volume = existing.volume() + 1;
                Candle c = new Candle(bucket, open, high, low, close, volume);
                storage.put(e.symbol(), interval, bucket, c);
                return c;
            }
        });
    }


    private double mid(double bid, double ask) {
        return (bid + ask) / 2.0;
    }
}