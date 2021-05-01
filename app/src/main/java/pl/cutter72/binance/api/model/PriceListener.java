package pl.cutter72.binance.api.model;

import android.app.Activity;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@SuppressWarnings("Convert2Lambda")
public class PriceListener {
    private static final String PRICE_ENDPOINT = "https://api.binance.com/api/v3/ticker/price?symbol=";
    private static final String PRICE_XRP_EUR = PRICE_ENDPOINT + "XRPEUR";
    private static final String PRICE_BTC_EUR = PRICE_ENDPOINT + "BTCEUR";
    private static final String PRICE_XRP_BTC = PRICE_ENDPOINT + "XRPBTC";

    private final Activity activity;
    private final TextView xrpEur;
    private final TextView btcEur;
    private final TextView xrpBtc;

    public PriceListener(Activity activity, TextView xrpEur, TextView btcEur, TextView xrpBtc) {
        this.activity = activity;
        this.xrpEur = xrpEur;
        this.btcEur = btcEur;
        this.xrpBtc = xrpBtc;
    }


    public void startListening() {
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Runnable request = new Runnable() {
                    @Override
                    public void run() {
                        StringBuilder sb = new StringBuilder();
                        URL url = null;
                        HttpURLConnection urlConnection = null;
                        try {
                            try {
                                url = new URL(PRICE_XRP_EUR);
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                            if (url != null) {
                                urlConnection = (HttpURLConnection) url.openConnection();
                            }
                            InputStream inputStream = null;
                            if (urlConnection != null) {
                                inputStream = new BufferedInputStream(urlConnection.getInputStream());
                            }
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                            while (true) {
                                String line = bufferedReader.readLine();
                                if (line != null) {
                                    sb.append(line);
                                } else {
                                    break;
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            if (urlConnection != null) {
                                urlConnection.disconnect();
                            }
                        }
                        Runnable updateText = new Runnable() {
                            @Override
                            public void run() {
                                xrpEur.setText(sb.toString());
                                Colorize.text(activity, xrpEur);
                            }
                        };
                        if (!xrpEur.getText().toString().equals(sb.toString())) {
                            activity.runOnUiThread(updateText);
                        }
                    }
                };
                new Thread(request).start();
                DelayedRunner.runDelayed(this, DelayedRunner.SECOND);
            }
        };
        runnable.run();
    }
}
