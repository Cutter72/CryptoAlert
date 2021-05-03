package pl.cutter72.binance.api.model;

import java.util.ArrayList;
import java.util.List;

public class Markets {
    public static String[] allSymbols;

    public static String[] search(String searchPhrase) {
        List<String> resultList = new ArrayList<>();
        if (searchPhrase != null) {
            if (searchPhrase.isEmpty()) {
                return allSymbols;
            } else {
                for (String symbol : allSymbols) {
                    if (symbol.contains(searchPhrase.toUpperCase())) {
                        resultList.add(symbol);
                    }
                }
                String[] arrayResult = new String[resultList.size()];
                return resultList.toArray(arrayResult);
            }
        } else {
            return allSymbols;
        }
    }
}
