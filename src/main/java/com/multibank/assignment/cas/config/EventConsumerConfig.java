package com.multibank.assignment.cas.config;

import com.multibank.assignment.cas.service.CandleAggregatorService;
import com.multibank.assignment.cas.service.EventBus;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class EventConsumerConfig {
    public EventConsumerConfig(EventBus bus, CandleAggregatorService aggregator) {
        this.bus = bus;
        this.aggregator = aggregator;
    }

    private final EventBus bus;
    private final CandleAggregatorService aggregator;


    @PostConstruct
    public void registerConsumer() {
        bus.register(aggregator::accept);
    }
}