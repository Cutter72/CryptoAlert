package pl.cutter72.crypto.alert.app.android.broadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import pl.cutter72.crypto.alert.app.android.activities.MainActivity;
import pl.cutter72.crypto.alert.app.android.other.NetworkUtil;

public class NetworkChangeReceiver extends BroadcastReceiver {
    private MainActivity mainActivity;

    public NetworkChangeReceiver(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {
        NetworkUtil.currentStatus = NetworkUtil.getConnectivityStatusString(context);
        if (NetworkUtil.currentStatus != 0) {
            mainActivity.startPriceListening();
        } else {
            mainActivity.stopPriceListening();
        }
    }
}
