package com.multibank.assignment.cas.scheduler;

import com.multibank.assignment.cas.model.BidAskEvent;
import com.multibank.assignment.cas.service.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;


@Component
public class RandomEventProducer {
    private static final Logger log = LoggerFactory.getLogger(RandomEventProducer.class);

    public RandomEventProducer(EventBus bus) {
        this.bus = bus;
    }

    private EventBus bus;
    private final Random rnd = new Random();
    private final String[] symbols = new String[]{"BTC-USD", "ETH-USD"};

    @Scheduled(fixedRate = 200)
    public void publishEvent() {
        String symbol = symbols[rnd.nextInt(symbols.length)];
        double base = switch (symbol) {
            case "BTC-USD" -> 90000 + rnd.nextDouble() * 5000;
            case "ETH-USD" -> 2000 + rnd.nextDouble() * 500;
            default -> 20 + rnd.nextDouble() * 40;
        };
        double bid = round(base - rnd.nextDouble());
        double ask = round(base + rnd.nextDouble());
        long ts = System.currentTimeMillis();
        BidAskEvent e = new BidAskEvent(symbol, bid, ask, ts);
        log.info("BidAskEvent Created: {}", e);
        bus.publish(e);
    }


    private double round(double v) {
        return Math.round(v * 100.0) / 100.0;
    }
}