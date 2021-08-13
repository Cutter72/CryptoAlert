package pl.cutter72.crypto.alert.app.binance;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@Setter
public class JsonCryptoSymbolWithPrice {
    public static final String SYMBOL_XRPEUR = "XRPEUR";
    public static final String SYMBOL_BTCEUR = "BTCEUR";
    public static final String SYMBOL_XRPBTC = "XRPBTC";

    private String symbol;
    private double price;

    public JsonCryptoSymbolWithPrice() {
        this.symbol = "noSymbol";
        this.price = -1;
    }
}
