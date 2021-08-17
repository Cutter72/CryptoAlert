package pl.cutter72.crypto.alert.app.android.broadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import pl.cutter72.crypto.alert.app.android.other.NetworkUtil;
import pl.cutter72.crypto.alert.app.binance.BackgroundDataListener;

public class NetworkChangeReceiver extends BroadcastReceiver {
    private final BackgroundDataListener backgroundDataListener;

    public NetworkChangeReceiver(BackgroundDataListener backgroundDataListener) {
        this.backgroundDataListener = backgroundDataListener;
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {
        System.out.println("NetworkUtil.onReceive");
        NetworkUtil.currentStatus = NetworkUtil.getConnectivityStatusString(context);
        if (NetworkUtil.currentStatus != 0) {
            System.out.println("NetworkUtil.currentStatus != 0");
            backgroundDataListener.startListening();
        } else {
            System.out.println("NetworkUtil.currentStatus == 0");
            backgroundDataListener.stopListening();
        }
    }
}
