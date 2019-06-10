package com.androidtutorialshub.loginregister.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Day implements Parcelable {

    private int id;
    private String interval;
    private boolean reserved;

    public Day(String interval, boolean reserved) {
        this.interval = interval;
        this.reserved = reserved;
    }

    public Day(int id, String interval, boolean reserved) {
        this.id = id;
        this.interval = interval;
        this.reserved = reserved;
    }

    public int getId() {
        return id;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    protected Day(Parcel in) {
        id = in.readInt();
        interval = in.readString();
        reserved = in.readByte() != 0;
    }

    public static final Creator<Day> CREATOR = new Creator<Day>() {
        @Override
        public Day createFromParcel(Parcel in) {
            return new Day(in);
        }

        @Override
        public Day[] newArray(int size) {
            return new Day[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(interval);
        parcel.writeByte((byte) (reserved ? 1 : 0));
    }
}
