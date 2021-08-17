package pl.cutter72.crypto.alert.app.android.other;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import pl.cutter72.crypto.alert.app.R;
import pl.cutter72.crypto.alert.app.other.DelayedRunner;

public final class Colorizer {
    private static final int COLORIZE_DURATION = 210;

    private Colorizer() {
        throw new UnsupportedOperationException();
    }

    @SuppressWarnings("Convert2Lambda")
    public static void background(Context context, final View view) {
        view.setBackgroundColor(context.getColor(R.color.binance_primary));
        DelayedRunner.runDelayed(new Runnable() {
            @Override
            public void run() {
                view.setBackgroundColor(Color.TRANSPARENT);
            }
        }, COLORIZE_DURATION);
    }

    @SuppressWarnings("Convert2Lambda")
    public static void text(final TextView textView) {
        final ColorStateList defaultTextColorStateList = textView.getTextColors();
        textView.setTextColor(textView.getContext().getColor(R.color.binance_primary));
        DelayedRunner.runDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setTextColor(defaultTextColorStateList);
            }
        }, COLORIZE_DURATION);
    }
}
