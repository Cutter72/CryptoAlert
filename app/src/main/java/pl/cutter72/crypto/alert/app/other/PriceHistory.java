package pl.cutter72.crypto.alert.app.other;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import pl.cutter72.crypto.alert.app.binance.JsonCryptoSymbolWithPrice;

public class PriceHistory {
    private Map<Date, JsonCryptoSymbolWithPrice> priceHistory;

    public PriceHistory() {
        this.priceHistory = new HashMap<>();
    }
}
