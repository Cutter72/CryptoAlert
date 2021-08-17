package pl.cutter72.crypto.alert.app.chart;


import androidx.annotation.NonNull;

import java.util.Arrays;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@Setter
public class CandlestickChartData {
    private SingleCandleData[] candlestickArray;

    public CandlestickChartData(String[][] candlestickDataArray) {
        this.candlestickArray = new SingleCandleData[candlestickDataArray.length];
        for (int i = 0; i < candlestickDataArray.length; i++) {
            this.candlestickArray[i] = new SingleCandleData(candlestickDataArray[i]);
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "CandlestickChartData{" +
                "candlestickArray=" + Arrays.toString(candlestickArray) +
                '}';
    }
}
