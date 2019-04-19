package com.androidtutorialshub.loginregister.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.androidtutorialshub.loginregister.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Madalina Bara on 06/04/2019
 */
public final class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "InventoryManagement";

    // User table name
    private static final String TABLE_USER = "user";
    // User Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";

    // table name
    static final String TABLE_EQUIPMENT = "equipment";
    // Table Columns names
    static final String EQUIPMENT_ID = "id";
    static final String EQUIPMENT_NAME = "name";
    static final String EQUIPMENT_ROOM = "room";

    /**
     * Constructor
     *
     * @param context an object of type {@code Context}
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("Create database: {}", DATABASE_NAME);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create user table sql query
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
                + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")";
        // create equipment table
        String CREATE_EQUIPMENT_TABLE = "CREATE TABLE " + TABLE_EQUIPMENT + "("
                + EQUIPMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + EQUIPMENT_NAME + " TEXT,"
                + EQUIPMENT_ROOM + " TEXT" + ")";
        db.execSQL(CREATE_USER_TABLE);
        Log.d("Table : {} created.", TABLE_USER);
        db.execSQL(CREATE_EQUIPMENT_TABLE);
        Log.d("Table : {} created.", CREATE_EQUIPMENT_TABLE);
        //initialize database with some equipments
        initializeDatabaseWithEquipments(db);
    }

    //TODO converteste ce e mai jos in query de sql, cum e aici in prima linie.
    private void initializeDatabaseWithEquipments(SQLiteDatabase equipmentDb) {
        equipmentDb.execSQL("INSERT INTO equipment (name, room) VALUES ('Equipment1', 'SpitalulMilitar')");
        //equipmentDb.addEquipment(new Equipment("X-ray Machine", "X-ray Lab"));
        //equipmentDb.addEquipment(new Equipment("Blood Chemistry Analyser", "Pathology Lab"));
        //equipmentDb.addEquipment(new Equipment("ECG/EKG", "ECG Room"));
        //equipmentDb.addEquipment(new Equipment("Largyngoscope", "OT"));
        //equipmentDb.addEquipment(new Equipment("Magnifying Glasses", "Surgery Cabinet"));
        //equipmentDb.addEquipment(new Equipment("Bio-hazard bags", "Store Room #1"));
        //equipmentDb.addEquipment(new Equipment("Antibacterial Wipes", "Store Room #2"));
        //equipmentDb.addEquipment(new Equipment("Hand Sanitizer", "Store Room #3"));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User table if exist
        final String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;
        //Drop Equipment table if exist
        final String DROP_EQUIPMENT_TABLE = "DROP TABLE IF EXISTS " + TABLE_EQUIPMENT;
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_EQUIPMENT_TABLE);
        // Create tables again
        onCreate(db);
    }

    /**
     * This method is to create user record
     *
     * @param user
     */
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public List<User> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_EMAIL,
                COLUMN_USER_NAME,
                COLUMN_USER_PASSWORD
        };
        // sorting orders
        String sortOrder = COLUMN_USER_NAME + " ASC";
        List<User> userList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }

    /**
     * This method to update user record
     *
     * @param user
     */
    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        // updating row
        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * This method is to delete user record
     *
     * @param user
     */
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    public boolean checkUser(String email) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @param password
     * @return true/false
     */
    public boolean checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }
}
