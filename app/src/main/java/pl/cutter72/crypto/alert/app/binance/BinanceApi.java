package pl.cutter72.crypto.alert.app.binance;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pl.cutter72.crypto.alert.app.chart.CandlestickChartData;

public class BinanceApi {
    public final static String INTERVAL_MINUTE = "1m";
    public final static int LIMIT_CHART_DATA = 60;
    private RestApi restApi;

    public BinanceApi(RestApi restApi) {
        this.restApi = restApi;
    }

    private static final String ALL_MARKETS_WITH_PRICE = "https://api.binance.com/api/v3/ticker/price";
    private static final String TEMPLATE_ENDPOINT_CANDLESTICK_CHART_DATA = "https://api.binance.com/api/v3/klines?symbol=%s&interval=%s&limit=%s";
    private static final String TEMPLATE_ENDPOINT_PRICE = "https://api.binance.com/api/v3/ticker/price?symbol=%s";

    @NonNull
    public CandlestickChartData getCandlestickChartData(String symbol, String interval, int limit) {
        System.out.println("getCandlestickChartData");
        String jsonResponseString = restApi.getResponse(String.format(TEMPLATE_ENDPOINT_CANDLESTICK_CHART_DATA, symbol, interval, limit));
        ObjectMapper objectMapper = new ObjectMapper();
        String[][] candlestickDataArrays = new String[0][];
        try {
            candlestickDataArrays = objectMapper.readValue(jsonResponseString, String[][].class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new CandlestickChartData(candlestickDataArrays);
    }

    @NonNull
    public CryptoPrice getCryptoPrice(String symbol) {
        System.out.println("getCryptoPrice");
        String jsonResponseString = restApi.getResponse(String.format(TEMPLATE_ENDPOINT_PRICE, symbol));
        System.out.println("jsonResponseString: " + jsonResponseString);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonResponseString, CryptoPrice.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new CryptoPrice();
        }
    }

    public void loadAllMarkets() {
        System.out.println("getAllMarkets");
        String response = restApi.getResponse(ALL_MARKETS_WITH_PRICE);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            CryptoPrice[] cryptoPrices = objectMapper.readValue(response, CryptoPrice[].class);
            Market.all = new String[cryptoPrices.length];
            for (int i = 0; i < cryptoPrices.length; i++) {
                Market.all[i] = cryptoPrices[i].getSymbol();
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
