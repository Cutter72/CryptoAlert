package pl.cutter72.binance.api.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@Setter
public class CandlestickData {
    private Date openTime;
    private double open;
    private double high;
    private double low;
    private double close;
    private double volume;
    private Date closeTime;
    private double quoteAssetVolume;
    private int numberOfTrades;
    private double takerBuyBaseAssetVolume;
    private double takerBuyQuoteAssetVolume;
    private double ignore;

    public CandlestickData(String[] candleStickData) {
        if (candleStickData != null && candleStickData.length == 12) {
            openTime = new Date(Long.parseLong(candleStickData[0]));
            open = Double.parseDouble(candleStickData[1]);
            high = Double.parseDouble(candleStickData[2]);
            low = Double.parseDouble(candleStickData[3]);
            close = Double.parseDouble(candleStickData[4]);
            volume = Double.parseDouble(candleStickData[5]);
            closeTime = new Date(Long.parseLong(candleStickData[6]));
            quoteAssetVolume = Double.parseDouble(candleStickData[7]);
            numberOfTrades = Integer.parseInt(candleStickData[8]);
            takerBuyBaseAssetVolume = Double.parseDouble(candleStickData[9]);
            takerBuyQuoteAssetVolume = Double.parseDouble(candleStickData[10]);
            ignore = Double.parseDouble(candleStickData[11]);
        }
    }

    @Override
    public String toString() {
        return "CandlestickData{" +
                "openTime=" + openTime +
                ", open=" + open +
                ", high=" + high +
                ", low=" + low +
                ", close=" + close +
                ", volume=" + volume +
                ", closeTime=" + closeTime +
                ", quoteAssetVolume=" + quoteAssetVolume +
                ", numberOfTrades=" + numberOfTrades +
                ", takerBuyBaseAssetVolume=" + takerBuyBaseAssetVolume +
                ", takerBuyQuoteAssetVolume=" + takerBuyQuoteAssetVolume +
                ", ignore=" + ignore +
                '}';
    }
}
