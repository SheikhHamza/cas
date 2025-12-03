package com.multibank.assignment.cas.model;

public enum Interval {
    S1(1000L),
    S5(5_000L),
    M1(60_000L),
    M15(15 * 60_000L),
    H1(60 * 60_000L);


    private final long millis;


    Interval(long millis) {
        this.millis = millis;
    }

    public long millis() {
        return millis;
    }


    public static Interval fromString(String s) {
        return switch (s) {
            case "1s", "1S", "S1" -> S1;
            case "5s", "5S", "S5" -> S5;
            case "1m", "1M", "M1" -> M1;
            case "15m", "15M", "M15" -> M15;
            case "1h", "1H", "H1" -> H1;
            default -> throw new IllegalArgumentException("Unsupported interval: " + s);
        };
    }
}