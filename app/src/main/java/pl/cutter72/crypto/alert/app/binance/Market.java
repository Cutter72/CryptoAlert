package pl.cutter72.crypto.alert.app.binance;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@Setter
public class Market {
    public static final String SYMBOL_XRPEUR = "XRPEUR";
    public static final String SYMBOL_BTCEUR = "BTCEUR";
    public static final String SYMBOL_XRPBTC = "XRPBTC";
    public static final String SYMBOL_ETHEUR = "ETHEUR";

    public static String[] all;

    private String marketSymbol;
    private String cryptoSymbol;
    private String priceSymbol;

    public Market(@NonNull String marketSymbol) {
        this.marketSymbol = marketSymbol;
        this.cryptoSymbol = marketSymbol.substring(0, 3);
        this.priceSymbol = marketSymbol.substring(3);
    }

    public static String[] search(String searchPhrase) {
        List<String> resultList = new ArrayList<>();
        if (searchPhrase != null) {
            if (searchPhrase.isEmpty()) {
                return all;
            } else {
                for (String symbol : all) {
                    if (symbol.contains(searchPhrase.toUpperCase())) {
                        resultList.add(symbol);
                    }
                }
                String[] arrayResult = new String[resultList.size()];
                return resultList.toArray(arrayResult);
            }
        } else {
            return all;
        }
    }
}
