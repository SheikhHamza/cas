# Candle Aggregation Service


This is a sample Spring Boot application that ingests simulated bid/ask events, aggregates them into OHLC candles across multiple intervals, stores them in-memory, and exposes a history REST endpoint.


## Run


```
./mvnw spring-boot:run
```


The application will start producing simulated market events for symbols `BTC-USD`, `ETH-USD`, `SOL-USD` every 200ms.


## API


`GET /history?symbol=BTC-USD&interval=1m&from=1620000000&to=1620000600`


- `interval` supports: `1s`, `5s`, `1m`, `15m`, `1h` (case insensitive-ish)
- `from` and `to` are **UNIX seconds**. Internally they are aligned to interval buckets.


## Notes
- Storage is in-memory for simplicity (ConcurrentSkipListMap per symbol/interval)
- Thread-safe map operations are used
- Lack comprehensive unit tests and integration tests because of the shortage of time. 
