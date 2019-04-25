package com.androidtutorialshub.loginregister.sql;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.androidtutorialshub.loginregister.model.Equipment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Madalina on 17.04.2019.
 */
public class EquipmentSqlCommander {

    private SQLiteOpenHelper sqLiteOpenHelper;

    public EquipmentSqlCommander(SQLiteOpenHelper sqLiteOpenHelper) {
        this.sqLiteOpenHelper = sqLiteOpenHelper;
    }

    /**
     * Adding new equipment
     *
     * @param equipment an object of type {@code Equipment}
     */
    public void addEquipment(Equipment equipment) {
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.EQUIPMENT_NAME, equipment.getName());
        values.put(DatabaseHelper.EQUIPMENT_ROOM, equipment.getRoom());
        values.put(DatabaseHelper.EQUIPMENT_GENERAL_DESCRIPTION, equipment.getDescription());
        values.put(DatabaseHelper.EQUIPMENT_TASK, equipment.getTask());
        values.put(DatabaseHelper.EQUIPMENT_MANUFACTURER, equipment.getManufacturer());
        values.put(DatabaseHelper.EQUIPMENT_MODEL, equipment.getModel());
        values.put(DatabaseHelper.EQUIPMENT_PREVENTIVE_MAINTENANCE, equipment.getPreventiveMaintenanceRequired());
        values.put(DatabaseHelper.EQUIPMENT_WARRANTY, equipment.getWarranty());
        values.put(DatabaseHelper.EQUIPMENT_MANUFACTURE_YEAR, equipment.getYearOfManufacture());
        values.put(DatabaseHelper.EQUIPMENT_QUANTITY, equipment.getQuantityAvailable());
        // Inserting Row
        db.insert(DatabaseHelper.TABLE_EQUIPMENT, null, values);
        db.close(); // Closing database connection
    }

    /**
     * Getting All Equipments
     *
     * @return equipments a list of object of type {@code List<Equipment>}
     */
    public List<Equipment> getAllEquipments() {
        List<Equipment> equipmentList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_EQUIPMENT;
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                final Equipment equipment = new Equipment();
                equipment.setId(Integer.parseInt(cursor.getString(0)));
                equipment.setName(cursor.getString(1));
                equipment.setRoom(cursor.getString(2));
                equipment.setDescription(cursor.getString(3));
                equipment.setTask(cursor.getString(4));
                equipment.setManufacturer(cursor.getString(5));
                equipment.setModel(cursor.getString(6));
                equipment.setPreventiveMaintenanceRequired(cursor.getString(7));
                equipment.setWarranty(cursor.getString(8));
                equipment.setYearOfManufacture(cursor.getString(9));
                equipment.setQuantityAvailable(cursor.getString(10));
                // Adding contact to list
                equipmentList.add(equipment);
            } while (cursor.moveToNext());
        }
        // return equipment list
        return equipmentList;
    }

    /**
     * Updating an equipment
     *
     * @param equipment object of type {@code Equipment }
     */
    public void updateEquipment(Equipment equipment) {
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.EQUIPMENT_NAME, equipment.getName());
        values.put(DatabaseHelper.EQUIPMENT_ROOM, equipment.getRoom());
        values.put(DatabaseHelper.EQUIPMENT_GENERAL_DESCRIPTION, equipment.getDescription());
        values.put(DatabaseHelper.EQUIPMENT_TASK, equipment.getTask());
        values.put(DatabaseHelper.EQUIPMENT_MANUFACTURER, equipment.getManufacturer());
        values.put(DatabaseHelper.EQUIPMENT_MODEL, equipment.getModel());
        values.put(DatabaseHelper.EQUIPMENT_PREVENTIVE_MAINTENANCE, equipment.getPreventiveMaintenanceRequired());
        values.put(DatabaseHelper.EQUIPMENT_WARRANTY, equipment.getWarranty());
        values.put(DatabaseHelper.EQUIPMENT_MANUFACTURE_YEAR, equipment.getYearOfManufacture());
        values.put(DatabaseHelper.EQUIPMENT_QUANTITY, equipment.getQuantityAvailable());
        // updating row
        db.update(DatabaseHelper.TABLE_EQUIPMENT, values, DatabaseHelper.EQUIPMENT_ID + " = ?",
                new String[]{String.valueOf(equipment.getId())});
    }

    /**
     * Deleting an equipment
     *
     * @param equipment object of type {@code Equipment }
     */
    public void deleteEquipment(Equipment equipment) {
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_EQUIPMENT, DatabaseHelper.EQUIPMENT_ID + " = ?",
                new String[]{String.valueOf(equipment.getId())});
        db.close();
    }


    // Getting one equipment by id
    public Equipment getInventory(int id) {
        SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_EQUIPMENT, new String[]{DatabaseHelper.EQUIPMENT_ID,
                        DatabaseHelper.EQUIPMENT_NAME, DatabaseHelper.EQUIPMENT_ROOM}, DatabaseHelper.EQUIPMENT_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        Equipment contact = null;
        if (cursor != null) {
            cursor.moveToFirst();
            contact = new Equipment(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), cursor.getString(2));
        }
        return contact;
    }

    // Getting equipment number
    public int getNumberOfEquipments() {
        String countQuery = "SELECT * FROM " + DatabaseHelper.TABLE_EQUIPMENT;
        SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        // return count
        return cursor.getCount();
    }

}
