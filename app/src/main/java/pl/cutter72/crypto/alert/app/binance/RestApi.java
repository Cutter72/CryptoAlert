package pl.cutter72.crypto.alert.app.binance;

import androidx.annotation.NonNull;

public interface RestApi {
    String getResponse(@NonNull String address);
}
