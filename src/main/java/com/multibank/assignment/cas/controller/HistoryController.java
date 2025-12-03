package com.multibank.assignment.cas.controller;

import com.multibank.assignment.cas.Util.IntervalUtil;
import com.multibank.assignment.cas.controller.dto.HistoryResponse;
import com.multibank.assignment.cas.controller.mapper.HistoryResponseMapper;
import com.multibank.assignment.cas.model.Interval;
import com.multibank.assignment.cas.service.CandleStorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HistoryController {


    private final CandleStorageService storage;

    public HistoryController(CandleStorageService storage) {
        this.storage = storage;
    }

    @GetMapping("/history")
    public ResponseEntity<HistoryResponse> history(
            @RequestParam String symbol,
            @RequestParam String interval,
            @RequestParam long from,
            @RequestParam long to) {

        Interval intervalEnum = Interval.fromString(interval);

        long fromBucket = IntervalUtil.timestampBucketFromInterval(intervalEnum, from);
        long toBucket = IntervalUtil.timestampBucketFromInterval(intervalEnum, to);


        var candles = storage.range(symbol, intervalEnum, fromBucket, toBucket);

        return ResponseEntity.ok(HistoryResponseMapper.fromCandles(candles));
    }


}