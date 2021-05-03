package pl.cutter72.binance.api.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PriceHistory {
    private Map<Date, JsonCryptoPrice> priceHistory;

    public PriceHistory() {
        this.priceHistory = new HashMap<>();
    }
}
