package com.multibank.assignment.cas.model;

import lombok.ToString;


public record BidAskEvent(String symbol, double bid, double ask, long timestamp) {}
