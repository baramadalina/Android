package com.androidtutorialshub.loginregister.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.androidtutorialshub.loginregister.model.Inventory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Madalina on 17.04.2019.
 */

public class DBHandler extends SQLiteOpenHelper {


    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME ="InventoryInfo";
    // table name
    private static final String TABLE_INVENTORY ="inventory";
    // Table Columns names
    private static final String KEY_ID ="id";
    private static final String KEY_NAME = "name";
    private static final String KEY_IN_ROOM = "inventory_room";


    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_CONTACTS_TABLE = "CREATE TABLE" + TABLE_INVENTORY + "("
        + KEY_ID + "INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
        + KEY_IN_ROOM + "TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {


        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INVENTORY);
// Creating tables again
        onCreate(db);

    }

    //// Adding new inventory
    public void addEquipemnt(Inventory inventory) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, inventory.getName());
        values.put(KEY_IN_ROOM, inventory.getRoom());
// Inserting Row
        db.insert(TABLE_INVENTORY, null, values);
        db.close(); // Closing database connection
    }


    // Getting one eq
    public Inventory getInventory(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_INVENTORY, new String[] { KEY_ID,
                        KEY_NAME, KEY_IN_ROOM }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Inventory contact = new Inventory(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));

        return contact;
    }


    // Getting All Eq
    public List<Inventory> getAllShops() {
        List<Inventory> inventoryList = new ArrayList<>();
// Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_INVENTORY;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Inventory inventory = new Inventory("X-ray Machine", "X-ray Lab");
                inventory.setId(Integer.parseInt(cursor.getString(0)));
                inventory.setName(cursor.getString(1));
                inventory.setRoom(cursor.getString(2));
// Adding contact to list
                inventoryList.add(inventory);
            } while (cursor.moveToNext());
        }
// return contact list
        return inventoryList;
    }


    // Getting shops Count
    public int getShopsCount() {
        String countQuery = "SELECT * FROM " + TABLE_INVENTORY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
// return count
        return cursor.getCount();
    }


    // Updating a shop
    public int updateShop(Inventory inventory) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, inventory.getName());
        values.put(KEY_IN_ROOM, inventory.getRoom());
// updating row
        return db.update(TABLE_INVENTORY, values, KEY_ID + " = ?",
                new String[]{String.valueOf(inventory.getId())});
    }

    // Deleting an equipment
    public void deleteShop(Inventory inventory) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_INVENTORY, KEY_ID + " = ?",
                new String[] { String.valueOf(inventory.getId()) });
        db.close();
    }






}

