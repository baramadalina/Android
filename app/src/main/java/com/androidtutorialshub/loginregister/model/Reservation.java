package com.androidtutorialshub.loginregister.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Reservation implements Parcelable {

    private int id;
    private List<Day> days;

    public List<Day> getDays() {
        return days;
    }

    public void setDays(List<Day> days) {
        this.days = days;
    }

    protected Reservation(Parcel in) {
        id = in.readInt();
        days = in.createTypedArrayList(Day.CREATOR);
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
        parcel.writeTypedList(days);
    }
}
