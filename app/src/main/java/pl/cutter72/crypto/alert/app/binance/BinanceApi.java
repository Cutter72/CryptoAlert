package pl.cutter72.crypto.alert.app.binance;

import androidx.annotation.NonNull;

import pl.cutter72.crypto.alert.app.chart.CandlestickChartData;

public interface BinanceApi {
    int LIMIT_CHART_DATA = 60;
    String INTERVAL_MINUTE = "1m";
    String ALL_MARKETS_WITH_PRICE = "https://api.binance.com/api/v3/ticker/price";
    String TEMPLATE_ENDPOINT_CANDLESTICK_CHART_DATA = "https://api.binance.com/api/v3/klines?symbol=%s&interval=%s&limit=%s";
    String TEMPLATE_ENDPOINT_PRICE = "https://api.binance.com/api/v3/ticker/price?symbol=%s";

    @NonNull
    CandlestickChartData getCandlestickChartData(String symbol, String interval, int limit);

    @NonNull
    CryptoPrice getCryptoPrice(String symbol);

    void loadAllMarkets();
}
