package com.multibank.assignment.cas.controller.mapper;

import com.multibank.assignment.cas.controller.dto.HistoryResponse;
import com.multibank.assignment.cas.model.Candle;

import java.util.ArrayList;
import java.util.List;

public class HistoryResponseMapper {

    public static HistoryResponse fromCandles(List<Candle> candles) {
        List<Long> t = new ArrayList<>();
        List<Double> o = new ArrayList<>();
        List<Double> h = new ArrayList<>();
        List<Double> l = new ArrayList<>();
        List<Double> c = new ArrayList<>();
        List<Long> v = new ArrayList<>();


        for (Candle candle : candles) {
            t.add(candle.time() / 1000);
            o.add(candle.open());
            h.add(candle.high());
            l.add(candle.low());
            c.add(candle.close());
            v.add(candle.volume());
        }
        return new HistoryResponse("ok", t, o, h, l, c, v);
    }
}
