package pl.cutter72.crypto.alert.app.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PriceHistory {
    private Map<Date, JsonCryptoSymbolWithPrice> priceHistory;

    public PriceHistory() {
        this.priceHistory = new HashMap<>();
    }
}
