package pl.cutter72.crypto.alert.app.binance;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import pl.cutter72.crypto.alert.app.android.other.NetworkUtil;

@Accessors(chain = true)
@Getter
@Setter
@SuppressWarnings("Convert2Lambda")
public class BackgroundDataListener {
    public static final long DEFAULT_INTERVAL_PRICE_MILLIS = 500;
    public static final long DEFAULT_INTERVAL_CHART_MILLIS = 1000 * 5;
    public static double higherThan = 999999;
    public static double lowerThan = -111111;
    private boolean isListening;
    private Market market;
    private PriceCallback priceCallback;
    private ChartCallback chartCallback;
    private BinanceApi binanceApi;
    private long priceListenInterval;
    private long chartListenInterval;
    private boolean hasMarketChangedPrice;
    private boolean hasMarketChangedChart;

    public BackgroundDataListener(Market market, PriceCallback priceCallback, ChartCallback chartCallback, BinanceApi binanceApi) {
        this.market = market;
        this.priceCallback = priceCallback;
        this.chartCallback = chartCallback;
        this.binanceApi = binanceApi;
        this.priceListenInterval = DEFAULT_INTERVAL_PRICE_MILLIS;
        this.chartListenInterval = DEFAULT_INTERVAL_CHART_MILLIS;
        this.isListening = false;
        this.hasMarketChangedPrice = false;
        this.hasMarketChangedChart = false;
    }

    public void setMarket(Market market) {
        this.market = market;
        this.hasMarketChangedPrice = true;
        this.hasMarketChangedChart = true;
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
                    if (hasMarketChangedPrice) {
                        hasMarketChangedPrice = false;
                        return;
                    } else {
                        if (isConnectedToInternet()) {
                            priceCallback.onPriceUpdate(binanceApi.getCryptoPrice(market.getMarketSymbol()));
                        } else {
                            stopListening();
                        }
                    }
                    waitInterval(priceListenInterval);
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
                    if (hasMarketChangedChart) {
                        hasMarketChangedChart = false;
                        return;
                    } else {
                        if (isConnectedToInternet()) {
                            chartCallback.onChartDataReceive(binanceApi.getCandlestickChartData(market.getMarketSymbol(), BinanceApi.INTERVAL_MINUTE, BinanceApi.LIMIT_CHART_DATA));
                        } else {
                            stopListening();
                        }
                    }
                    waitInterval(chartListenInterval);
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
