package pl.cutter72.binance.api.model;

import java.util.Locale;

public final class TextUtil {
    public static String getFormattedNumber(double number, int decimalPlaces) {
        switch (decimalPlaces) {
            case 2:
                return String.format(Locale.getDefault(), "%.2f", number);
            case 4:
                return String.format(Locale.getDefault(), "%.4f", number);
            case 8:
            default:
                return String.format(Locale.getDefault(), "%.8f", number);
        }
    }
}
