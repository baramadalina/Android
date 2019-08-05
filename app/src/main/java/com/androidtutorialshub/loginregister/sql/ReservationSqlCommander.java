package com.androidtutorialshub.loginregister.sql;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.androidtutorialshub.loginregister.model.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Madalina on 17.07.2019.
 */
public class ReservationSqlCommander {

    private SQLiteOpenHelper sqLiteOpenHelper;

    public ReservationSqlCommander(SQLiteOpenHelper sqLiteOpenHelper) {
        this.sqLiteOpenHelper = sqLiteOpenHelper;
    }

    /**
     * Adding new reservation event
     *
     * @param reservation an object of type {@code addReservation}
     */
    public void addReservation(Reservation reservation) {
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        Random r = new Random();
        int reservationId = r.nextInt(10000 - 1) + 1;
        values.put(DatabaseHelper.RESERVATION_ID, reservationId);
        values.put(DatabaseHelper.RESERVATION_TITLE, reservation.getTitle());
        values.put(DatabaseHelper.RESERVATION_LOCATION, reservation.getLocation());
        values.put(DatabaseHelper.RESERVATION_DETAILS, reservation.getDetails());
        values.put(DatabaseHelper.RESERVATION_USER_EMAIL, reservation.getUserEmail());
        values.put(DatabaseHelper.RESERVATION_DURATION, reservation.getDuration());
        values.put(DatabaseHelper.RESERVATION_START_TIME, reservation.getStartTime());
        values.put(DatabaseHelper.RESERVATION_EQUIPMENT_ID, reservation.getEquipmentId());
        try {
            // Inserting Row
            db.insert(DatabaseHelper.TABLE_RESERVATION, null, values);
        } catch (Exception e) {
            reservationId = r.nextInt(10000 - 1) + 1;
            values.put(DatabaseHelper.RESERVATION_ID, reservationId);
            db.insert(DatabaseHelper.TABLE_RESERVATION, null, values);
        } finally {
            // Closing database connection
            db.close();
        }
    }

    // Getting one reservation by title, location and equipment id
    public Reservation getCreatedReservation(String title) {
        SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_RESERVATION, new String[]{DatabaseHelper.RESERVATION_ID, DatabaseHelper.RESERVATION_TITLE, DatabaseHelper.RESERVATION_LOCATION,
                        DatabaseHelper.RESERVATION_DETAILS, DatabaseHelper.RESERVATION_DURATION, DatabaseHelper.RESERVATION_START_TIME,
                        DatabaseHelper.RESERVATION_USER_EMAIL, DatabaseHelper.RESERVATION_EQUIPMENT_ID}, DatabaseHelper.RESERVATION_TITLE + "=?",
                new String[]{title}, null, null, null, null);
        Reservation reservation = null;
        if (cursor != null) {
            cursor.moveToFirst();
            reservation = new Reservation(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), cursor.getString(2), cursor.getString(3),
                    cursor.getString(4), cursor.getString(5), cursor.getString(6),
                    Integer.parseInt(cursor.getString(7)));
        }
        db.close();
        return reservation;
    }

    /**
     * Getting Reservations event for a specific equipment
     *
     * @return reservations events a list of object of type {@code List<Reservation>}
     */
    public List<Reservation> getReservationsForEquipmentId(int equipmentId) {
        List<Reservation> reservationList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_RESERVATION + " WHERE " + DatabaseHelper.RESERVATION_EQUIPMENT_ID + "=" + equipmentId;
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                final Reservation reservation = new Reservation();
                reservation.setId(Integer.parseInt(cursor.getString(0)));
                reservation.setTitle(cursor.getString(1));
                reservation.setLocation(cursor.getString(2));
                reservation.setDetails(cursor.getString(3));
                reservation.setUserEmail(cursor.getString(4));
                reservation.setStartTime(cursor.getString(5));
                reservation.setDuration(cursor.getString(6));
                reservation.setEquipmentId(cursor.getInt(7));
                // Adding reservation to list
                reservationList.add(reservation);
            } while (cursor.moveToNext());
        }
        db.close();
        // return reservation events list
        return reservationList;
    }

    /**
     * Getting All reservations event
     *
     * @return reservations events a list of object of type {@code List<Reservation>}
     */
    public List<Reservation> getAllReservations() {
        List<Reservation> reservationList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_RESERVATION;
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                final Reservation reservation = new Reservation();
                reservation.setId(Integer.parseInt(cursor.getString(0)));
                reservation.setTitle(cursor.getString(1));
                reservation.setLocation(cursor.getString(2));
                reservation.setDetails(cursor.getString(3));
                reservation.setStartTime(cursor.getString(4));
                reservation.setDuration(cursor.getString(5));
                reservation.setEquipmentId(cursor.getInt(6));
                reservation.setUserEmail(cursor.getString(7));
                // Adding reservation to list
                reservationList.add(reservation);
            } while (cursor.moveToNext());
        }
        db.close();
        // return reservation events list
        return reservationList;
    }

    /**
     * Updating a reservation
     *
     * @param reservation object of type {@code Reservation }
     */
    public void updateReservation(Reservation reservation) {
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.RESERVATION_TITLE, reservation.getTitle());
        values.put(DatabaseHelper.RESERVATION_LOCATION, reservation.getLocation());
        values.put(DatabaseHelper.RESERVATION_DETAILS, reservation.getDetails());
        values.put(DatabaseHelper.RESERVATION_START_TIME, reservation.getStartTime());
        values.put(DatabaseHelper.RESERVATION_DURATION, reservation.getDuration());
        values.put(DatabaseHelper.RESERVATION_EQUIPMENT_ID, reservation.getEquipmentId());
        values.put(DatabaseHelper.RESERVATION_USER_EMAIL, reservation.getUserEmail());
        // updating row
        db.update(DatabaseHelper.TABLE_RESERVATION, values, DatabaseHelper.RESERVATION_ID + " = ?",
                new String[]{String.valueOf(reservation.getId())});
        db.close();
    }

    /**
     * Deleting a reservation
     *
     * @param reservation object of type {@code Reservation }
     */
    public void deleteReservation(Reservation reservation) {
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_RESERVATION, DatabaseHelper.RESERVATION_ID + " = ?",
                new String[]{String.valueOf(reservation.getId())});
        db.close();
    }


}