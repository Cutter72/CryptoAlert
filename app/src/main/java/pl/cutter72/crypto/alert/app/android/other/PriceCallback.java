package pl.cutter72.crypto.alert.app.android.other;

import pl.cutter72.crypto.alert.app.binance.CryptoPrice;

public interface PriceCallback {
    void onPriceUpdate(CryptoPrice cryptoPrice);
}
