package pl.cutter72.binance.api;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import pl.cutter72.binance.api.model.PriceListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickStartListener(View view) {
        new PriceListener(this, findViewById(R.id.xrpEurText), null, null).startListening();
    }
}