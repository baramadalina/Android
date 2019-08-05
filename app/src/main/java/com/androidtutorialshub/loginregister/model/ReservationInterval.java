package com.androidtutorialshub.loginregister.model;

import android.support.annotation.NonNull;

import org.joda.time.DateTime;

public class ReservationInterval implements Comparable<ReservationInterval> {

    private DateTime startDate;
    private DateTime endDate;

    public ReservationInterval(DateTime startDate, DateTime endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ReservationInterval{");
        sb.append("startDate=").append(startDate);
        sb.append(", endDate=").append(endDate);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int compareTo(@NonNull ReservationInterval reservationInterval) {
        if (this.startDate.equals(reservationInterval.getStartDate())) {
            return 0;
        } else if (startDate.isAfter(reservationInterval.getStartDate())) {
            return 1;
        } else {
            return -1;
        }
    }
}
