package pl.cutter72.binance.api.model;

import android.app.Activity;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

import pl.cutter72.binance.api.MainActivity;

@SuppressWarnings("Convert2Lambda")
public class PriceListener {
    private static final String PRICE_ENDPOINT = "https://api.binance.com/api/v3/ticker/price?symbol=";
    private static final String CANDLESTICK_ENDPOINT_FORMAT = "https://api.binance.com/api/v3/klines?symbol=%s&interval=%s&limit=%s";
    private boolean isListening;
    private final Activity activity;
    private final TextView xrpEurTextView;
    private final TextView btcEurTextView;
    private final TextView xrpBtcTextView;
    private final TextView xrpBtcEurTextView;
    private JsonCryptoPrice xrpEurPrice;
    private JsonCryptoPrice btcEurPrice;
    private JsonCryptoPrice xrpBtcPrice;

    public PriceListener(Activity activity, TextView xrpEurTextView, TextView btcEurTextView, TextView xrpBtcTextView, TextView xrpBtcEurTextView) {
        this.activity = activity;
        this.xrpEurTextView = xrpEurTextView;
        this.btcEurTextView = btcEurTextView;
        this.xrpBtcTextView = xrpBtcTextView;
        this.xrpBtcEurTextView = xrpBtcEurTextView;
        this.xrpEurPrice = new JsonCryptoPrice();
        this.btcEurPrice = new JsonCryptoPrice();
        this.xrpBtcPrice = new JsonCryptoPrice();
        this.isListening = false;
    }


    public void startListening() {
        if (!this.isListening) {
            this.isListening = true;
            listenFor(JsonCryptoPrice.SYMBOL_XRPEUR);
            listenFor(JsonCryptoPrice.SYMBOL_BTCEUR);
            listenFor(JsonCryptoPrice.SYMBOL_XRPBTC);
        }
    }

    public void stopListening() {
        this.isListening = false;
    }

    private void listenFor(String cryptoSymbol) {
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Runnable request = new Runnable() {
                    @Override
                    public void run() {
                        updateCryptoData(getJsonCryptoPrice(cryptoSymbol), cryptoSymbol);
                    }
                };
                new Thread(request).start();
                if (NetworkUtil.currentStatus != 0) {
                    DelayedRunner.runDelayed(this, DelayedRunner.SECOND);
                } else {
                    stopListening();
                }
            }
        };
        runnable.run();
    }

    public void getCandlestickChartData(String cryptoSymbol, String interval, int limit) {
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Runnable request = new Runnable() {
                    @Override
                    public void run() {
                        MainActivity.candlestickChartData = getJsonCandlestickChartData(cryptoSymbol, interval, limit);
                        activity.runOnUiThread(((MainActivity) activity)::updatedChartData);
                    }
                };
                new Thread(request).start();
            }
        };
        runnable.run();
    }

    private void updateCryptoData(JsonCryptoPrice cryptoPrice, String cryptoSymbol) {
        switch (cryptoSymbol) {
            case JsonCryptoPrice.SYMBOL_XRPEUR:
                xrpEurPrice = cryptoPrice;
                updatePriceTextView(xrpEurPrice, xrpEurTextView, 4);
                break;
            case JsonCryptoPrice.SYMBOL_BTCEUR:
                btcEurPrice = cryptoPrice;
                updatePriceTextView(btcEurPrice, btcEurTextView, 2);
                break;
            case JsonCryptoPrice.SYMBOL_XRPBTC:
                xrpBtcPrice = cryptoPrice;
                updatePriceTextView(xrpBtcPrice, xrpBtcTextView, 8);
                break;
            default:
                System.err.println("cryptoSymbol error");
        }
        double xrpBtcEur = xrpBtcPrice.getPrice() * btcEurPrice.getPrice();
        String xrpBtcEurPrice = "XRPBTC_EUR: " + TextUtil.getFormattedNumber(xrpBtcEur, 4);
        activity.runOnUiThread(() -> xrpBtcEurTextView.setText(xrpBtcEurPrice));
    }

    @Nullable
    private JsonCryptoPrice getJsonCryptoPrice(String cryptoSymbol) {
        System.out.println("getJsonCryptoPrice");
        HttpURLConnection urlConnection = null;
        JsonCryptoPrice cryptoPrice = new JsonCryptoPrice();
        if (cryptoSymbol != null) {
            try {
                URL url = new URL(PRICE_ENDPOINT + cryptoSymbol);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String jsonResponseString = getJsonString(bufferedReader);
                System.out.println("jsonResponseString: " + jsonResponseString);
                ObjectMapper objectMapper = new ObjectMapper();
                cryptoPrice = objectMapper.readValue(jsonResponseString, JsonCryptoPrice.class);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        }
        return cryptoPrice;
    }

    @Nullable
    private CandlestickChartData getJsonCandlestickChartData(String cryptoSymbol, String interval, int limit) {
        System.out.println("getJsonCandlestickChartData");
        HttpURLConnection urlConnection = null;
        CandlestickChartData candlestickChartData = null;
        if (cryptoSymbol != null) {
            try {
                URL url = new URL(String.format(CANDLESTICK_ENDPOINT_FORMAT, cryptoSymbol, interval, limit));
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String jsonResponseString = getJsonString(bufferedReader);
                System.out.println("jsonResponseString: " + jsonResponseString);
                ObjectMapper objectMapper = new ObjectMapper();
                String[][] candlestickDataArrays = objectMapper.readValue(jsonResponseString, String[][].class);
                System.out.println("candlestickDataArrays.length: " + candlestickDataArrays.length);
                System.out.println("candlestickDataArrays[0].length: " + candlestickDataArrays[0].length);
                candlestickChartData = new CandlestickChartData(candlestickDataArrays);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        }
        return candlestickChartData;
    }

    private String getJsonString(BufferedReader bufferedReader) throws IOException {
        StringBuilder sb = new StringBuilder();
        while (true) {
            String line = bufferedReader.readLine();
            if (line != null) {
                sb.append(line);
            } else {
                break;
            }
        }
        return sb.toString();
    }

    private void updatePriceTextView(JsonCryptoPrice cryptoPrice, TextView textViewToUpdate, int decimalPlaces) {
        String formattedPrice = getFormattedPriceText(cryptoPrice, decimalPlaces);
        Runnable updateText = new Runnable() {
            @Override
            public void run() {
                textViewToUpdate.setText(formattedPrice);
                Colorize.text(activity, textViewToUpdate);
            }
        };
        if (!textViewToUpdate.getText().toString().equals(formattedPrice)) {
            activity.runOnUiThread(updateText);
        }
    }

    private String getFormattedPriceText(JsonCryptoPrice cryptoPrice, int decimalPlaces) {
        return String.format(Locale.getDefault(), "%s: %s", cryptoPrice.getSymbol(), TextUtil.getFormattedNumber(cryptoPrice.getPrice(), decimalPlaces));
    }
}
