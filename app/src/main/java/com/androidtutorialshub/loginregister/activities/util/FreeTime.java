package com.androidtutorialshub.loginregister.activities.util;

/**
 * Created by Bara Madalina on 6/18/2019.
 */
public class FreeTime {

    public static final String API_START_TIMESTAMP = "start_time";
    public static final String API_TIMES_FIT = "times_fit";


    private long startTimeStamp;
    private int timesFit;
    private DateManager dateManager;


    public FreeTime(int timesFit, long startTimeStamp) {
        this.timesFit = timesFit;
        this.startTimeStamp = startTimeStamp;
        dateManager = new DateManager(startTimeStamp);
    }

    public String getTimeString(long startTimeStamp) {
        return dateManager.getReadableDayDateTimeString(startTimeStamp);
    }

    public int getRepetitionsCount() {
        return timesFit;
    }

    public long getStartTimeStamp() {
        return startTimeStamp;
    }
}
