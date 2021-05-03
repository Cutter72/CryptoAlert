package pl.cutter72.binance.api.model;


import java.util.Arrays;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@Setter
public class CandlestickChartData {
    private CandlestickData[] candlestickArray;

    public CandlestickChartData(String[][] candlestickDataArray) {
        this.candlestickArray = new CandlestickData[candlestickDataArray.length];
        for (int i = 0; i < candlestickDataArray.length; i++) {
            this.candlestickArray[i] = new CandlestickData(candlestickDataArray[i]);
        }
    }

    @Override
    public String toString() {
        return "CandlestickChartData{" +
                "candlestickArray=" + Arrays.toString(candlestickArray) +
                '}';
    }
}
