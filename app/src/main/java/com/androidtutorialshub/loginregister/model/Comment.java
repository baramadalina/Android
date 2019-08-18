package com.androidtutorialshub.loginregister.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.androidtutorialshub.loginregister.activities.util.DateManager;

/**
 * Created by Madalina Bara on 8/04/2019.
 */
public class Comment implements Parcelable {
    public static final String API_ID = "id";
    public static final String API_AUTHOR_ID = "author_id";
    public static final String API_EQUIPMENT_ID = "equipment_id";
    public static final String API_CONTENT = "content";
    public static final String API_CREATED_AT = "created_at";
    public static final String API_AUTHOR_NAME = "author_name";

    private int id;
    private int authorID;
    private int equipmentID;
    private String content;
    private String authorName;
    private Long createdAtTimestamp;
    private DateManager mDateManager;

    public Comment() {}

    public Comment(int id, int authorID, int equipmentID, String content, String authorName, Long createdAtTimestamp) {
        this.id = id;
        this.authorID = authorID;
        this.equipmentID = equipmentID;
        this.content = content;
        this.authorName = authorName;
        this.createdAtTimestamp = createdAtTimestamp;
        mDateManager = new DateManager(this.createdAtTimestamp);
    }

    protected Comment(Parcel in) {
        id = in.readInt();
        authorID = in.readInt();
        equipmentID = in.readInt();
        content = in.readString();
        authorName = in.readString();
        if (in.readByte() == 0) {
            createdAtTimestamp = null;
        } else {
            createdAtTimestamp = in.readLong();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAuthorID() {
        return authorID;
    }

    public void setAuthorID(int authorID) {
        this.authorID = authorID;
    }

    public int getEquipmentID() {
        return equipmentID;
    }

    public void setEquipmentID(int equipmentID) {
        this.equipmentID = equipmentID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getCreatedAtTimestamp() {
        return createdAtTimestamp;
    }

    public void setCreatedAtTimestamp(Long createdAtTimestamp) {
        this.createdAtTimestamp = createdAtTimestamp;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getDateString() {
        return mDateManager.getReadableDateString(createdAtTimestamp);
    }

    public String getDateTimeString() {
        return mDateManager.getReadableDateTimeString(createdAtTimestamp);
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(authorID);
        dest.writeInt(equipmentID);
        dest.writeString(content);
        dest.writeString(authorName);
        if (createdAtTimestamp == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(createdAtTimestamp);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Comment{");
        sb.append("id=").append(id);
        sb.append(", authorID=").append(authorID);
        sb.append(", equipmentID=").append(equipmentID);
        sb.append(", content='").append(content).append('\'');
        sb.append(", authorName='").append(authorName).append('\'');
        sb.append(", createdAtTimestamp=").append(createdAtTimestamp);
        sb.append('}');
        return sb.toString();
    }
}
