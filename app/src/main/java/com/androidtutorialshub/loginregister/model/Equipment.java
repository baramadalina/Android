package com.androidtutorialshub.loginregister.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Madalina on 17.04.2019.
 */

public class Equipment implements Parcelable {

    private int id;
    private String name;
    private String room;

    public Equipment(String name, String room) {
        this.name = name;
        this.room = room;
    }

    public Equipment(int id, String name, String room) {
        this.id = id;
        this.name = name;
        this.room = room;
    }

    protected Equipment(Parcel in) {
        id = in.readInt();
        name = in.readString();
        room = in.readString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public static final Creator<Equipment> CREATOR = new Creator<Equipment>() {
        @Override
        public Equipment createFromParcel(Parcel in) {
            return new Equipment(in);
        }

        @Override
        public Equipment[] newArray(int size) {
            return new Equipment[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(room);
    }
}
