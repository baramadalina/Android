package com.androidtutorialshub.loginregister.model;

public class BusyTimeSlot {

    private String startTime;
    private String duration;

    public BusyTimeSlot(String startTime, String duration) {
        this.startTime = startTime;
        this.duration = duration;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BusyTimeSlot{");
        sb.append("startTime='").append(startTime).append('\'');
        sb.append(", duration='").append(duration).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
