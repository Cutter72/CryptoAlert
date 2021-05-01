package pl.cutter72.binance.api.model;

import android.os.Handler;

@SuppressWarnings("deprecation")
public final class DelayedRunner {
    public static final long SECOND = 1000;
    public static final long MINUTE = SECOND * 60;

    private DelayedRunner() {
        throw new UnsupportedOperationException();
    }

    public static void runDelayed(Runnable runnable, long delay) {
        Handler mHandler = new Handler();
        mHandler.postDelayed(runnable, delay);
    }
}
