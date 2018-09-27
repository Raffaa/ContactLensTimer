package com.raffinato.contactlensreminder;

import android.os.Parcel;
import android.os.Parcelable;

import org.joda.time.DateTime;
import org.joda.time.Days;

public class Lens implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Lens> CREATOR = new Parcelable.Creator<Lens>() {
        @Override
        public Lens createFromParcel(Parcel in) {
            return new Lens(in);
        }

        @Override
        public Lens[] newArray(int size) {
            return new Lens[size];
        }
    };
    private final DateTime initialDate;
    private Duration duration;
    private DateTime expDate;
    private String name;


    public Lens(String name, Duration duration, DateTime date) {
        this.name = name;
        this.duration = duration;
        this.expDate = date.plusDays(duration.getTime());
        this.initialDate = date;
    }

    public Lens(Duration duration, DateTime date) {
        this.name = "";
        this.duration = duration;
        this.expDate = date.plusDays(duration.getTime());
        this.initialDate = date;
    }

    Lens(Parcel in) {
        duration = (Duration) in.readValue(Duration.class.getClassLoader());
        expDate = (DateTime) in.readValue(DateTime.class.getClassLoader());
        name = in.readString();
        initialDate = (DateTime) in.readValue(DateTime.class.getClassLoader());
    }

    public Duration getDuration() {
        return duration;
    }

    public DateTime getExpDate() {
        return expDate;
    }

    public DateTime getInitialDate() {
        return initialDate;
    }

    public Days getRemainingTime() {
        return Days.daysBetween(new DateTime().toDateTime(), this.expDate.toDateTime()).plus(1);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(duration);
        dest.writeValue(expDate);
        dest.writeString(name);
        dest.writeValue(initialDate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public enum Duration {

        DAILY(1), BIWEEKLY(14), MONTHLY(30), QUARTERLY(120), ANNUAL(365);

        private final int t;

        Duration(int t) {
            this.t = t;
        }

        public static Duration fromInt(int i) {
            switch (i) {
                case 1:
                    return Duration.DAILY;
                case 14:
                    return Duration.BIWEEKLY;
                case 30:
                    return Duration.MONTHLY;
                case 120:
                    return Duration.QUARTERLY;
                case 365:
                    return Duration.ANNUAL;
                default:
                    return null;
            }
        }

        public int getTime() {
            return this.t;
        }
    }
}