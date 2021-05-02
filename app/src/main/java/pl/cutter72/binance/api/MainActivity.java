package pl.cutter72.binance.api;

import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import pl.cutter72.binance.api.model.NetworkChangeReceiver;
import pl.cutter72.binance.api.model.PriceListener;

public class MainActivity extends AppCompatActivity {

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

    }
}