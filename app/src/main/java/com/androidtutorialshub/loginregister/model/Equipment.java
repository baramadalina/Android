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
    private String description;
    private String task;
    private String manufacturer;
    private String model;
    private String preventiveMaintenanceRequired;
    private String warranty;
    private String yearOfManufacture;
    private String quantityAvailable;

    public Equipment() {}

    public Equipment(int id, String name, String room) {
        this.id = id;
        this.name = name;
        this.room = room;
    }

    public Equipment(String name, String room, String description, String task,
                     String manufacturer, String model, String preventiveMaintenanceRequired,
                     String warranty, String yearOfManufacture, String quantityAvailable) {
        this.name = name;
        this.room = room;
        this.description = description;
        this.task = task;
        this.manufacturer = manufacturer;
        this.model = model;
        this.preventiveMaintenanceRequired = preventiveMaintenanceRequired;
        this.warranty = warranty;
        this.yearOfManufacture = yearOfManufacture;
        this.quantityAvailable = quantityAvailable;
    }

    public Equipment(int id, String name, String room, String description, String task,
                     String manufacturer, String model, String preventiveMaintenanceRequired,
                     String warranty, String yearOfManufacture, String quantityAvailable) {
        this.id = id;
        this.name = name;
        this.room = room;
        this.description = description;
        this.task = task;
        this.manufacturer = manufacturer;
        this.model = model;
        this.preventiveMaintenanceRequired = preventiveMaintenanceRequired;
        this.warranty = warranty;
        this.yearOfManufacture = yearOfManufacture;
        this.quantityAvailable = quantityAvailable;
    }

    protected Equipment(Parcel in) {
        id = in.readInt();
        name = in.readString();
        room = in.readString();
        description = in.readString();
        task = in.readString();
        manufacturer = in.readString();
        model = in.readString();
        preventiveMaintenanceRequired = in.readString();
        warranty = in.readString();
        yearOfManufacture = in.readString();
        quantityAvailable = in.readString();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPreventiveMaintenanceRequired() {
        return preventiveMaintenanceRequired;
    }

    public void setPreventiveMaintenanceRequired(String preventiveMaintenanceRequired) {
        this.preventiveMaintenanceRequired = preventiveMaintenanceRequired;
    }

    public String getWarranty() {
        return warranty;
    }

    public void setWarranty(String warranty) {
        this.warranty = warranty;
    }

    public String getYearOfManufacture() {
        return yearOfManufacture;
    }

    public void setYearOfManufacture(String yearOfManufacture) {
        this.yearOfManufacture = yearOfManufacture;
    }

    public String getQuantityAvailable() {
        return quantityAvailable;
    }

    public void setQuantityAvailable(String quantityAvailable) {
        this.quantityAvailable = quantityAvailable;
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
        parcel.writeString(description);
        parcel.writeString(task);
        parcel.writeString(manufacturer);
        parcel.writeString(model);
        parcel.writeString(preventiveMaintenanceRequired);
        parcel.writeString(warranty);
        parcel.writeString(yearOfManufacture);
        parcel.writeString(quantityAvailable);
    }

    @Override
    public String toString() {
        return new StringBuilder().append("Equipment{").append("id=").append(id)
                .append(", name='").append(name).append('\'')
                .append(", room='").append(room).append('\'')
                .append(", description='").append(description)
                .append('\'').append(", task='").append(task)
                .append('\'').append(", manufacturer='").append(manufacturer).append('\'')
                .append(", model='").append(model).append('\'')
                .append(", preventiveMaintenanceRequired='").append(preventiveMaintenanceRequired).append('\'')
                .append(", warranty='").append(warranty).append('\'')
                .append(", yearOfManufacture='").append(yearOfManufacture).append('\'')
                .append(", quantityAvailable='").append(quantityAvailable).append('\'').append('}').toString();
    }
}
