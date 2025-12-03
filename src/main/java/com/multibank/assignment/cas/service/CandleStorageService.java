package com.multibank.assignment.cas.service;

import com.multibank.assignment.cas.model.Candle;
import com.multibank.assignment.cas.model.Interval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;

@Component
public class CandleStorageService {
    private static final Logger log = LoggerFactory.getLogger(CandleStorageService.class);
    private final ConcurrentMap<String, ConcurrentMap<Interval, ConcurrentSkipListMap<Long, Candle>>> store = new ConcurrentHashMap<>();


    public void put(String symbol, Interval interval, long time, Candle candle) {
        var m = store.computeIfAbsent(symbol, s -> new ConcurrentHashMap<>());
        var imap = m.computeIfAbsent(interval, i -> new ConcurrentSkipListMap<>());
        log.info("Updating Storage: Putting {}  at {}", candle, time);
        imap.put(time, candle);
    }

    public List<Candle> range(String symbol, Interval interval, long fromInclusive, long toInclusive) {
        var imap = store.getOrDefault(symbol, new ConcurrentHashMap<>()).get(interval);
        if (imap == null) return Collections.emptyList();
        return new ArrayList<>(imap.subMap(fromInclusive, true, toInclusive, true).values());
    }
}
