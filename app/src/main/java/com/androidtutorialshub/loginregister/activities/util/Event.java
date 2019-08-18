package com.androidtutorialshub.loginregister.activities.util;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Objects;

/**
 * Created by Madalina Bara on 7/21/2019.
 */
public class Event implements Parcelable {
    public static final String API_ID = "id";
    public static final String API_OWNER_ID = "owner_id";
    public static final String API_TITLE = "event";
    public static final String API_DETAILS = "details";
    public static final String API_LOCATION = "location";
    public static final String API_START_TIMESTAMP = "start_time";
    public static final String API_DURATION = "duration";
    public static final String API_RESERVED_BY_USER = "userEmail";
    public static final String API_RESERVED_EQUIPMENT_ID = "equipmentId";

    private int id;
    private int owner_id;
    private String title;
    private String details;
    private String location;
    private long start_timestamp;
    private int duration;
    private String userEmail;
    private int equipmentId;
    private DateManager mDateManager;

    public Event(int id, int owner_id, String title, String details,
                 String location, long start_timestamp, int duration, String userEmail, int equipmentId) {
        this.id = id;
        this.owner_id = owner_id;
        this.title = title;
        this.details = details;
        this.location = location;
        this.start_timestamp = start_timestamp;
        this.duration = duration;
        this.userEmail = userEmail;
        this.equipmentId = equipmentId;
        mDateManager = new DateManager(this.start_timestamp);
    }

    public Event(Parcel source) {
        this.id = source.readInt();
        this.owner_id = source.readInt();
        this.duration = source.readInt();
        this.title = source.readString();
        this.details = source.readString();
        this.location = source.readString();
        this.start_timestamp = source.readLong();
        this.userEmail = source.readString();
        this.equipmentId = source.readInt();

        mDateManager = new DateManager(this.start_timestamp);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(int owner_id) {
        this.owner_id = owner_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getStart_timestamp() {
        return start_timestamp;
    }

    public void setStart_timestamp(long start_timestamp) {
        this.start_timestamp = start_timestamp;
        this.mDateManager.setTimeStamp(this.start_timestamp);
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDateString() {
        return mDateManager.getReadableDateString(start_timestamp);
    }

    public String getTimeString() {
        return mDateManager.getReadableTimeString(start_timestamp);
    }

    public String getDateTimeString() {
        return mDateManager.getReadableDateTimeString(start_timestamp);
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.owner_id);
        dest.writeInt(this.duration);
        dest.writeString(this.title);
        dest.writeString(this.details);
        dest.writeString(this.location);
        dest.writeLong(this.start_timestamp);
        dest.writeString(this.userEmail);
        dest.writeInt(this.equipmentId);
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel source) {
            return new Event(source);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }

    };


    public static class EventHash {
        private static final String KEY_ID = "id";
        private static final String KEY_OWNER_ID = "owner_id";
        public static final String KEY_TITLE = "event";
        private static final String KEY_DETAILS = "details";
        private static final String KEY_LOCATION = "location";
        private static final String KEY_START_TIMESTAMP = "start_time";
        private static final String KEY_DURATION = "duration";
        public static final String KEY_TIME = "time";
        public static final String KEY_DATE = "date";
        private static final String KEY_DATE_TIME = "date_time";
        private static final String RESERVED_BY_USER_ = "userEmail";
        private static final String RESERVED_EQUIPMENT_ID = "equipmentId";


        public static HashMap<String, String> getHashFromEvent(Event event) {
            HashMap<String, String> eventHash = new HashMap<>();
            eventHash.put(KEY_ID, Integer.toString(event.getId()));
            eventHash.put(KEY_OWNER_ID, Integer.toString((event.getOwner_id())));
            eventHash.put(KEY_TITLE, event.getTitle());
            eventHash.put(KEY_DETAILS, event.getDetails());
            eventHash.put(KEY_LOCATION, event.getLocation());
            eventHash.put(KEY_START_TIMESTAMP, Long.toString(event.getStart_timestamp()));
            eventHash.put(KEY_DURATION, Integer.toString(event.getDuration()));
            eventHash.put(KEY_TIME, event.getTimeString());
            eventHash.put(KEY_DATE, event.getDateString());
            eventHash.put(KEY_DATE_TIME, event.getDateTimeString());
            eventHash.put(RESERVED_BY_USER_, event.getUserEmail());
            eventHash.put(RESERVED_EQUIPMENT_ID, String.valueOf(event.getEquipmentId()));
            return eventHash;
        }

        public static Event getEventFromHash(HashMap<String, String> eventHash) {
            int id = Integer.parseInt(Objects.requireNonNull(eventHash.get(KEY_ID)));
            int owner_id = Integer.parseInt(Objects.requireNonNull(eventHash.get(KEY_OWNER_ID)));
            int duration = Integer.parseInt(Objects.requireNonNull(eventHash.get(KEY_DURATION)));
            String title = eventHash.get(KEY_TITLE);
            String details = eventHash.get(KEY_DETAILS);
            String location = eventHash.get(KEY_LOCATION);
            long start_timestamp = Long.parseLong(Objects.requireNonNull(eventHash.get(KEY_START_TIMESTAMP)));
            String userEmail = eventHash.get(RESERVED_BY_USER_);
            int equipmentId = Integer.parseInt(Objects.requireNonNull(eventHash.get(RESERVED_EQUIPMENT_ID)));

            return new Event(id, owner_id, title, details,
                    location, start_timestamp, duration, userEmail, equipmentId);
        }
    }
}
