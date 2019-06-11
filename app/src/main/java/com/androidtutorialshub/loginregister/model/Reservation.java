package com.androidtutorialshub.loginregister.model;

import android.os.Parcel;
import android.os.Parcelable;


public class Reservation implements Parcelable {

    private int id;
    private String interval;
    private int equipmentId;

    public Reservation(String interval, int equipmentId) {
        this.interval = interval;
        this.equipmentId = equipmentId;
    }

    public Reservation(int id, String interval, int equipmentId) {
        this.id = id;
        this.interval = interval;
        this.equipmentId = equipmentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    protected Reservation(Parcel in) {
        id = in.readInt();
        interval = in.readString();
        equipmentId = in.readInt();
    }

    public static final Creator<Reservation> CREATOR = new Creator<Reservation>() {
        @Override
        public Reservation createFromParcel(Parcel in) {
            return new Reservation(in);
        }

        @Override
        public Reservation[] newArray(int size) {
            return new Reservation[size];
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
        parcel.writeInt(equipmentId);
    }
}
