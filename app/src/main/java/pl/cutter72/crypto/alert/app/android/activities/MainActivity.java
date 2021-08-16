package pl.cutter72.crypto.alert.app.android.activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;

import java.util.ArrayList;
import java.util.List;

import pl.cutter72.crypto.alert.app.R;
import pl.cutter72.crypto.alert.app.android.broadcastreceivers.NetworkChangeReceiver;
import pl.cutter72.crypto.alert.app.android.other.PriceListener;
import pl.cutter72.crypto.alert.app.binance.Market;
import pl.cutter72.crypto.alert.app.chart.CandlestickChartData;
import pl.cutter72.crypto.alert.app.chart.CandlestickData;

@SuppressWarnings("Convert2Lambda")
public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "channelId";
    private static final int LIMIT = 60;
    public static CandlestickChartData candlestickChartData = null;
    private EditText higherThan;
    private EditText lowerThan;
    private NetworkChangeReceiver networkChangeReceiver = null;
    private PriceListener priceListener = null;
    private CandleStickChart xrpEurChart = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeChart();
        initializeAlarms();
        registerNetworkStateChangeReceiver();
        priceListener = new PriceListener(this, findViewById(R.id.xrpEurText), findViewById(R.id.btcEurText), findViewById(R.id.xrpBtcText), findViewById(R.id.xrpBtcEurText));
    }

    private void initializeAlarms() {
        higherThan = findViewById(R.id.higherThan);
        lowerThan = findViewById(R.id.lowerThan);
        higherThan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                try {
                    PriceListener.higherThan = Double.parseDouble(((EditText) v).getText().toString());
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
            }
        });
        lowerThan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                try {
                    PriceListener.lowerThan = Double.parseDouble(((EditText) v).getText().toString());
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void initializeChart() {
        xrpEurChart = findViewById(R.id.xrpEurChart);
        xrpEurChart.setHighlightPerDragEnabled(true);
        xrpEurChart.setDrawBorders(true);
        xrpEurChart.setBorderColor(getColor(R.color.white));
        Description description = xrpEurChart.getDescription();
        description.setText(Market.SYMBOL_XRPEUR);
        description.setTextColor(getColor(R.color.white));
//        xrpEurChart.setMarker(new MarkerView(this, R.layout.support_simple_spinner_dropdown_item));
//        xrpEurChart.setDrawMarkers(true);
        xrpEurChart.setHighlightPerDragEnabled(true);
        xrpEurChart.setHighlightPerTapEnabled(true);
//        xrpEurChart.setHigh(true);

        YAxis leftAxis = xrpEurChart.getAxisLeft();
        leftAxis.setDrawLabels(false);
        leftAxis.setDrawGridLines(false);
        YAxis rightAxis = xrpEurChart.getAxisRight();
        rightAxis.setDrawGridLines(true);
        rightAxis.setGridColor(getColor(R.color.chart_grid_lines));
        rightAxis.setGridLineWidth(0.8f);
        rightAxis.setTextColor(getColor(R.color.white));
        xrpEurChart.requestDisallowInterceptTouchEvent(false);

        XAxis xAxis = xrpEurChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(getColor(R.color.white));
        xAxis.setDrawGridLines(true);// disable x axis grid lines
        xAxis.setDrawLabels(true);
        xAxis.setGridColor(getColor(R.color.chart_grid_lines));
        xAxis.setGridLineWidth(0.8f);
        xAxis.setLabelRotationAngle(-45f);
        xAxis.setAvoidFirstLastClipping(true);

        Legend l = xrpEurChart.getLegend();
        l.setEnabled(false);
    }

    public void updatedChartData() {
        if (candlestickChartData != null) {
            List<CandleEntry> candleEntryList = new ArrayList<>();
            float i = -LIMIT;
            for (CandlestickData candlestickData : candlestickChartData.getCandlestickArray()) {
                candleEntryList.add(new CandleEntry(i++, (float) candlestickData.getHigh(), (float) candlestickData.getLow(), (float) candlestickData.getOpen(), (float) candlestickData.getClose()));
            }
            CandleDataSet set1 = new CandleDataSet(candleEntryList, "DataSet 1");
            set1.setColor(Color.rgb(80, 80, 80));
            set1.setShadowColorSameAsCandle(true);
            set1.setShadowWidth(0.8f);
            set1.setDecreasingColor(getColor(R.color.candlestick_decreasing));
            set1.setDecreasingPaintStyle(Paint.Style.FILL);
            set1.setIncreasingColor(getColor(R.color.candlestick_increasing));
            set1.setIncreasingPaintStyle(Paint.Style.FILL);
            set1.setNeutralColor(getColor(R.color.candlestick_decreasing));
            set1.setDrawValues(false);


            // create a data object with the datasets
            CandleData data = new CandleData(set1);


            // set data
            xrpEurChart.setData(data);
            xrpEurChart.invalidate();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterNetworkStateChangeReceiver();
    }

    public void onClickRefreshChart(View view) {
        getCandlestickData();
    }

    private void getCandlestickData() {
        priceListener.getCandlestickChartData(Market.SYMBOL_XRPEUR, "1m", LIMIT);
    }

    public void makeAlert(String title, String contentText) {
        //Set notification content
        long[] vibration = {0, 1000, 500, 1000, 500, 1000, 500, 1000, 500, 1000, 500, 1000, 500, 1000, 500, 1000, 500, 1000, 500, 1000, 500, 1000, 500, 1000};
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(contentText)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(vibration)
                .setSound(uri);
        //Create a channel for notification in Android 8+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.crypto_alert_channel);
            String description = getString(R.string.crypto_alarms);
            int importance = NotificationManager.IMPORTANCE_HIGH;
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

    public void startPriceListening() {
        priceListener.startListening();
    }

    public void stopPriceListening() {
        priceListener.stopListening();
    }

    private void registerNetworkStateChangeReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        // Add network connectivity change action.
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        // Set broadcast receiver priority.
        intentFilter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        // Create a network change broadcast receiver.
        networkChangeReceiver = new NetworkChangeReceiver(this);
        // Register the broadcast receiver with the intent filter object.
        registerReceiver(networkChangeReceiver, intentFilter);
        networkChangeReceiver.onReceive(this, getIntent());
    }

    private void unregisterNetworkStateChangeReceiver() {
        // If the broadcast receiver is not null then unregister it.
        // This action is better placed in activity onDestroy() method.
        if (this.networkChangeReceiver != null) {
            unregisterReceiver(this.networkChangeReceiver);
        }
    }
}