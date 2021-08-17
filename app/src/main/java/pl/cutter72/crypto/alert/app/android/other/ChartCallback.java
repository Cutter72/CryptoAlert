package pl.cutter72.crypto.alert.app.android.other;

import pl.cutter72.crypto.alert.app.chart.CandlestickChartData;

public interface ChartCallback {
    void onChartDataReceive(CandlestickChartData candlestickChartData);
}
