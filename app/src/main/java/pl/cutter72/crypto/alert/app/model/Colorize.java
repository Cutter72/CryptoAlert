package pl.cutter72.crypto.alert.app.model;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import pl.cutter72.crypto.alert.app.R;

public final class Colorize {
    private static final int COLORIZE_DURATION = 222;

    private Colorize() {
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
    public static void text(Context context, final TextView textView) {
        final ColorStateList defaultTextColorStateList = textView.getTextColors();
        textView.setTextColor(context.getColor(R.color.binance_primary));
        DelayedRunner.runDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setTextColor(defaultTextColorStateList);
            }
        }, COLORIZE_DURATION);
    }
}
