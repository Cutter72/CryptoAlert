package pl.cutter72.crypto.alert.app.binance;

import pl.cutter72.crypto.alert.app.chart.CandlestickChartData;

public interface ChartCallback {
    void onChartDataReceive(CandlestickChartData candlestickChartData);
}
