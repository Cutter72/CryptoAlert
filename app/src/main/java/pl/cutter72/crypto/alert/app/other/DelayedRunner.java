package pl.cutter72.crypto.alert.app.other;

import android.os.Handler;

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
