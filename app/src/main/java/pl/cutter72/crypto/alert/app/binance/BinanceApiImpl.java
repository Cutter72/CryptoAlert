package pl.cutter72.crypto.alert.app.binance;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pl.cutter72.crypto.alert.app.chart.CandlestickChartData;

public class BinanceApiImpl implements BinanceApi {
    private RestApi restApi;

    public BinanceApiImpl(RestApi restApi) {
        this.restApi = restApi;
    }

    @NonNull
    @Override
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
    @Override
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

    @Override
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
