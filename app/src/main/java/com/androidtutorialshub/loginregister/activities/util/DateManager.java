package com.androidtutorialshub.loginregister.activities.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Madalina Bara on 6/21/2019.
 */
public class DateManager {
    private GregorianCalendar mCalendar;
    private long mTimeStamp;

    public DateManager(long unixTimeStamp) {
        mCalendar = new GregorianCalendar();
        this.setTimeStamp(unixTimeStamp);
    }

    public DateManager() {
        mCalendar = new GregorianCalendar();
    }

    // get normal timestamp from Unix timestamp
    // unix timestamp is in seconds
    // normal timestamp is in milliseconds
    public static long fromUnixTimeStamp(long unixTimeStamp) {
        return unixTimeStamp * 1000;
    }

    public static long toUnixTimeStamp(long timeStamp) {
        return timeStamp / 1000;
    }

    public void setTimeStamp(long unixTimeStamp) {
        mTimeStamp = fromUnixTimeStamp(unixTimeStamp);
        mCalendar.setTimeInMillis(mTimeStamp);
    }

    public String getReadableDateString(long timeStampToDisplay) {
        DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
        return dateFormat.format(timeStampToDisplay);
    }

    public void setDate(int year, int month, int day) {
        mCalendar.set(year, month, day);
    }

    public String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(this.mCalendar.getTimeInMillis());
    }

    public String getTime() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        return dateFormat.format(this.mCalendar.getTimeInMillis());
    }

    public void setTime(int hour, int minute, int second) {
        mCalendar.set(Calendar.HOUR_OF_DAY, hour);
        mCalendar.set(Calendar.MINUTE, minute);
        mCalendar.set(Calendar.SECOND, second);
    }

    public String getReadableTimeString(long start_timestamp) {
        DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        return dateFormat.format(start_timestamp);
    }

    public String getReadableDateTimeString(long timeStampToDisplay) {
        DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy hh:mm a");
        return dateFormat.format(timeStampToDisplay);
    }

    public String getReadableDayDateTimeString(long mTimeStampToDisplay) {
        DateFormat dateFormat = new SimpleDateFormat("EEE, MMM dd, hh:mm a");
        return dateFormat.format(mTimeStampToDisplay);
    }
}
