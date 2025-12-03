package com.multibank.assignment.cas.service;

import com.multibank.assignment.cas.model.BidAskEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


@Component
public class EventBus {
    private final List<Consumer<BidAskEvent>> listeners = new ArrayList<>();


    public void publish(BidAskEvent e) {
        for (var l : listeners) l.accept(e);
    }


    public void register(Consumer<BidAskEvent> listener) {
        listeners.add(listener);
    }
}