package pl.cutter72.crypto.alert.app.binance;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class BinanceApi {

    public static void getAllMarkets() {
        System.out.println("getAllMarkets");
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(Endpoint.ALL_MARKETS_WITH_PRICE);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String jsonResponseString = getJsonString(bufferedReader);
            System.out.println(url.toString() + "\nresponse: " + jsonResponseString);
            ObjectMapper objectMapper = new ObjectMapper();
            Market[] markets = objectMapper.readValue(jsonResponseString, Market[].class);
            Market.all = new String[markets.length];
            for (int i = 0; i < markets.length; i++) {
                Market.all[i] = markets[i].getSymbol();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    private static String getJsonString(BufferedReader bufferedReader) throws IOException {
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
}
