package com.asgarov.aktienVerwaltung.table;

import java.io.Serializable;

/* Simple DTO */
public class KursDatei implements Serializable {

    private String date;
    private String open;
    private String high;
    private String low;
    private String close;
    private String adjClose;
    private String volume;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getLow() {
        return low;
    }

    public void setLow(String low) {
        this.low = low;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getAdjClose() {
        return adjClose;
    }

    public void setAdjClose(String adjClose) {
        this.adjClose = adjClose;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "KursDatei{" +
                "date='" + date + '\'' +
                ", open='" + open + '\'' +
                ", high='" + high + '\'' +
                ", low='" + low + '\'' +
                ", close='" + close + '\'' +
                ", adjClose='" + adjClose + '\'' +
                ", volume='" + volume + '\'' +
                '}';
    }
}
