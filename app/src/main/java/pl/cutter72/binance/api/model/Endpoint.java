package pl.cutter72.binance.api.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@Setter
public class Endpoint {
    public static final String ALL_MARKETS_WITH_PRICE = "https://api.binance.com/api/v3/ticker/price";
    private static final String PRICE = "https://api.binance.com/api/v3/ticker/price";
    private static final String CANDLESTICK_DATA = "https://api.binance.com/api/v3/klines";
    private String base;

    private Endpoint() {
    }

    static class Builder {
        private String base;

        public Endpoint build() {
            return new Endpoint();
        }
    }
}
