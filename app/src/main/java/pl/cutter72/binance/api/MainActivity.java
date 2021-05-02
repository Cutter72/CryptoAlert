package pl.cutter72.binance.api;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import pl.cutter72.binance.api.model.NetworkChangeReceiver;
import pl.cutter72.binance.api.model.PriceListener;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "channelId";
    private NetworkChangeReceiver networkChangeReceiver = null;
    private PriceListener priceListener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);// Create an IntentFilter instance.
        registerNetworkStateChangeReceiver();
        priceListener = new PriceListener(this, findViewById(R.id.xrpEurText), findViewById(R.id.btcEurText), findViewById(R.id.xrpBtcText), findViewById(R.id.xrpBtcEurText));
        networkChangeReceiver.onReceive(this, getIntent());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterNetworkStateCHangeReceiver();
    }

    public void startPriceListening() {
        priceListener.startListening();
    }

    public void stopPriceListening() {
        priceListener.stopListening();
    }

    private void registerNetworkStateChangeReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        // Add network connectivity change action.
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        // Set broadcast receiver priority.
        intentFilter.setPriority(100);
        // Create a network change broadcast receiver.
        networkChangeReceiver = new NetworkChangeReceiver(this);
        // Register the broadcast receiver with the intent filter object.
        registerReceiver(networkChangeReceiver, intentFilter);
    }

    private void unregisterNetworkStateCHangeReceiver() {
        // If the broadcast receiver is not null then unregister it.
        // This action is better placed in activity onDestroy() method.
        if (this.networkChangeReceiver != null) {
            unregisterReceiver(this.networkChangeReceiver);
        }
    }

    public void onClickMakeAlert(View view) {
        //Set notification content
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("textTitle")
                .setContentText("textContent")
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        //Create a channel for notification in ANdroid 8+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.binance_api_channel);
            String description = getString(R.string.binance_alarms);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        //Show notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(369, builder.build());
    }
}