package pl.cutter72.crypto.alert.app.binance;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@Setter
public class CryptoPrice {
    private double price;
    private String symbol;
}
