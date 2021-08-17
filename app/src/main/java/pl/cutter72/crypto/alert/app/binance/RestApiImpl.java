package pl.cutter72.crypto.alert.app.binance;

import androidx.annotation.NonNull;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class RestApiImpl implements RestApi {

    @Override
    public String getResponse(@NonNull String address) {
        System.out.println("getResponse");
        String response = "";
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(address);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            response = getJsonString(bufferedReader);
            System.out.println(address + " response: " + response);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return response;
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
}
