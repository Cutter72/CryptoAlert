package pl.cutter72.crypto.alert.app.other;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import pl.cutter72.crypto.alert.app.binance.Market;

public class PriceHistory {
    private Map<Date, Market> priceHistory;

    public PriceHistory() {
        this.priceHistory = new HashMap<>();
    }
}
