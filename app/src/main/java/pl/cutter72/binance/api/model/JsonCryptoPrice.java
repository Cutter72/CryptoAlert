package pl.cutter72.binance.api.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@Setter
public class JsonCryptoPrice {
    public static final String SYMBOL_XRPEUR = "XRPEUR";
    public static final String SYMBOL_BTCEUR = "BTCEUR";
    public static final String SYMBOL_XRPBTC = "XRPBTC";

    private String symbol;
    private double price;

    public JsonCryptoPrice() {
        this.symbol = "noSymbol";
        this.price = -1;
    }
}
