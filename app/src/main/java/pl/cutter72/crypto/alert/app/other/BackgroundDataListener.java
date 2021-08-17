package pl.cutter72.crypto.alert.app.other;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.cutter72.crypto.alert.app.android.other.ChartCallback;
import pl.cutter72.crypto.alert.app.android.other.NetworkUtil;
import pl.cutter72.crypto.alert.app.android.other.PriceCallback;
import pl.cutter72.crypto.alert.app.binance.BinanceApi;
import pl.cutter72.crypto.alert.app.binance.Market;

@Accessors(chain = true)
@Getter
@Setter
@SuppressWarnings("Convert2Lambda")
public class BackgroundDataListener {
    private static final String PRICE_ENDPOINT = "https://api.binance.com/api/v3/ticker/price?symbol=";
    private static final String CANDLESTICK_ENDPOINT_FORMAT = "https://api.binance.com/api/v3/klines?symbol=%s&interval=%s&limit=%s";
    public static final long DEFAULT_INTERVAL_PRICE = 500;
    public static final long DEFAULT_INTERVAL_CHART = 1000 * 10;
    private boolean isListening;
    public static double higherThan = 999999;
    public static double lowerThan = -111111;
    private Market market;
    private PriceCallback priceCallback;
    private ChartCallback chartCallback;
    private BinanceApi binanceApi;
    private long priceListenInterval;
    private long chartListenInterval;

    public BackgroundDataListener(Market market, PriceCallback priceCallback, ChartCallback chartCallback, BinanceApi binanceApi) {
        this.market = market;
        this.priceCallback = priceCallback;
        this.chartCallback = chartCallback;
        this.binanceApi = binanceApi;
        this.priceListenInterval = DEFAULT_INTERVAL_PRICE;
        this.chartListenInterval = DEFAULT_INTERVAL_CHART;
        this.isListening = false;
    }


    public void startListening() {
        System.out.println("startListening");
        if (!this.isListening) {
            if (isConnectedToInternet()) {
                this.isListening = true;
                listenForPrice();
                listenForChart();
            }
        }
    }

    public void stopListening() {
        this.isListening = false;
    }

    private void listenForPrice() {
        System.out.println("listenForPrice");
        Runnable priceListener = new Runnable() {
            @Override
            public void run() {
                System.out.println("listenForPrice.run");
                while (isListening) {
                    System.out.println("listenForPrice.while");
                    waitInterval(priceListenInterval);
                    if (isConnectedToInternet()) {
                        priceCallback.onPriceUpdate(binanceApi.getCryptoPrice(market.getMarketSymbol()));
                    } else {
                        stopListening();
                    }
                }
            }
        };
        new Thread(priceListener).start();
    }

    private void listenForChart() {
        System.out.println("listenForChart");
        Runnable chartListener = new Runnable() {
            @Override
            public void run() {
                System.out.println("listenForChart.run");
                while (isListening) {
                    System.out.println("listenForChart.while");
                    waitInterval(chartListenInterval);
                    if (isConnectedToInternet()) {
                        chartCallback.onChartDataReceive(binanceApi.getCandlestickChartData(market.getMarketSymbol(), BinanceApi.INTERVAL_MINUTE, BinanceApi.LIMIT_CHART_DATA));
                    } else {
                        stopListening();
                    }
                }
            }
        };
        new Thread(chartListener).start();
    }

    private void waitInterval(long interval) {
        System.out.println("waitInterval: " + interval);
        synchronized (this) {
            try {
                wait(interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isConnectedToInternet() {
        return NetworkUtil.currentStatus != 0;
    }
}
