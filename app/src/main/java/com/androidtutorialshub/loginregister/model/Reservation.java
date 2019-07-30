package com.androidtutorialshub.loginregister.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Reservation implements Parcelable {

    private int id;
    private String title;
    private String location;
    private String details;
    private String duration;
    private String startTime;
    private String userEmail;
    private int equipmentId;

    public Reservation() {}

    public Reservation(String title, String location, String details, String duration, String startTime, String userEmail, int equipmentId) {
        this.title = title;
        this.location = location;
        this.details = details;
        this.duration = duration;
        this.startTime = startTime;
        this.userEmail = userEmail;
        this.equipmentId = equipmentId;
    }

    public Reservation(int id, String title, String location, String details, String duration, String startTime, String userEmail, int equipmentId) {
        this.id = id;
        this.title = title;
        this.location = location;
        this.details = details;
        this.duration = duration;
        this.startTime = startTime;
        this.userEmail = userEmail;
        this.equipmentId = equipmentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    protected Reservation(Parcel in) {
        id = in.readInt();
        title = in.readString();
        location = in.readString();
        details = in.readString();
        duration = in.readString();
        startTime = in.readString();
        userEmail = in.readString();
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
        parcel.writeString(title);
        parcel.writeString(location);
        parcel.writeString(details);
        parcel.writeString(userEmail);
        parcel.writeString(startTime);
        parcel.writeString(duration);
        parcel.writeInt(equipmentId);
    }
}
