package pl.cutter72.crypto.alert.app.binance;

public interface PriceCallback {
    void onPriceUpdate(CryptoPrice cryptoPrice);
}
