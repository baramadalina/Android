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
    static final String COLUMN_USER_ID = "user_id";
    static final String COLUMN_USER_NAME = "user_name";
    static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";

    // Equipment table name
    static final String TABLE_EQUIPMENT = "equipment";
    // Equipment Table Columns names
    static final String EQUIPMENT_ID = "id";
    static final String EQUIPMENT_NAME = "name";
    static final String EQUIPMENT_ROOM = "room";
    static final String EQUIPMENT_GENERAL_DESCRIPTION = "description";
    static final String EQUIPMENT_TASK = "task";
    static final String EQUIPMENT_MANUFACTURER = "manufacturer";
    static final String EQUIPMENT_MODEL = "model";
    static final String EQUIPMENT_PREVENTIVE_MAINTENANCE = "preventive_maintenance";
    static final String EQUIPMENT_WARRANTY = "warranty";
    static final String EQUIPMENT_MANUFACTURE_YEAR = "year_of_manufacture";
    static final String EQUIPMENT_QUANTITY = "quantity_available";

    // Reservation table name
    static final String TABLE_RESERVATION = "reservation";
    // Reservation Table Columns
    static final String RESERVATION_ID = "id";
    static final String RESERVATION_TITLE = "title";
    static final String RESERVATION_LOCATION = "location";
    static final String RESERVATION_DETAILS = "details";
    static final String RESERVATION_START_TIME = "start_time";
    static final String RESERVATION_DURATION = "duration";
    static final String RESERVATION_EQUIPMENT_ID = "equipment_id";
    static final String RESERVATION_USER_EMAIL = "user_email";

    // Comment table name
    static final String TABLE_COMMENT = "comment";
    // Comment table columns
    static final String COMMENT_ID = "id";
    static final String COMMENT_AUTHOR_ID = "author_id";
    static final String COMMENT_AUTHOR_NAME = "author_name";
    static final String COMMENT_EQUIPMENT_ID = "equipment_id";
    static final String COMMENT_CONTENT = "content";
    static final String COMMENT_CREATED_AT = "created_at";

    /**
     * Constructor
     *
     * @param context an object of type {@code Context}
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("Create database: {}", DATABASE_NAME);
        //Keep this below line here to drop database when it's necessary
        //context.deleteDatabase(DATABASE_NAME);
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
                + EQUIPMENT_ROOM + " TEXT," + EQUIPMENT_GENERAL_DESCRIPTION + " TEXT,"
                + EQUIPMENT_TASK + " TEXT," + EQUIPMENT_MANUFACTURER + " TEXT,"
                + EQUIPMENT_MODEL + " TEXT," + EQUIPMENT_PREVENTIVE_MAINTENANCE + " TEXT,"
                + EQUIPMENT_WARRANTY + " TEXT," + EQUIPMENT_MANUFACTURE_YEAR + " TEXT,"
                + EQUIPMENT_QUANTITY + " TEXT" + ")";

        // create table reservation
        String CREATE_RESERVATION_TABLE = "CREATE TABLE " + TABLE_RESERVATION + "("
                + RESERVATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + RESERVATION_TITLE + " TEXT," + RESERVATION_LOCATION + " TEXT,"
                + RESERVATION_DETAILS + " TEXT," + RESERVATION_USER_EMAIL + " TEXT,"
                + RESERVATION_START_TIME + " TEXT," + RESERVATION_DURATION + " TEXT,"
                + RESERVATION_EQUIPMENT_ID + " INTEGER" + ")";

        // create table reservation
        String CREATE_COMMENT_TABLE = "CREATE TABLE " + TABLE_COMMENT + "("
                + COMMENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COMMENT_AUTHOR_ID + " INTEGER," + COMMENT_AUTHOR_NAME + " TEXT,"
                + COMMENT_EQUIPMENT_ID + " INTEGER," + COMMENT_CONTENT + " TEXT,"
                + COMMENT_CREATED_AT + " TEXT" + ")";


        db.execSQL(CREATE_USER_TABLE);
        Log.d("Table : {} created.", TABLE_USER);
        db.execSQL(CREATE_EQUIPMENT_TABLE);
        Log.d("Table : {} created.", TABLE_EQUIPMENT);
        db.execSQL(CREATE_RESERVATION_TABLE);
        Log.d("Table : {} created.", TABLE_RESERVATION);
        db.execSQL(CREATE_COMMENT_TABLE);
        Log.d("Table : {} created.", TABLE_COMMENT);
        //initialize database when the application start
        initializeDatabaseWithUsers(db);
        initializeDatabaseWithEquipments(db);
        initializeDatabaseWithReservations(db);
        initializeDatabaseWithComments(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User table if exist
        final String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;
        //Drop Equipment table if exist
        final String DROP_EQUIPMENT_TABLE = "DROP TABLE IF EXISTS " + TABLE_EQUIPMENT;
        //Drop Equipment table if exist
        final String DROP_RESERVATION_TABLE = "DROP TABLE IF EXISTS " + TABLE_RESERVATION;
        //Drop Equipment table if exist
        final String DROP_COMMENT_TABLE = "DROP TABLE IF EXISTS " + TABLE_COMMENT;
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_EQUIPMENT_TABLE);
        db.execSQL(DROP_RESERVATION_TABLE);
        db.execSQL(DROP_COMMENT_TABLE);
        // Create tables again
        onCreate(db);
    }

    /**
     * Insert values into 'equipment' table when the application startup
     * with few medical equipments
     *
     * @param equipmentDb an object of type {@code SQLiteDatabase}
     */
    private void initializeDatabaseWithEquipments(SQLiteDatabase equipmentDb) {
        equipmentDb.execSQL("INSERT INTO equipment (name, room, description) VALUES ('Equipment1', 'Room 1', 'dummy description')");
        equipmentDb.execSQL("INSERT INTO equipment (name, room) VALUES ('Anaesthesia Machine', 'OT')");
        equipmentDb.execSQL("INSERT INTO equipment (name, room) VALUES ('X-ray Machine', 'X-ray Lab')");
        equipmentDb.execSQL("INSERT INTO equipment (name, room) VALUES ('Blood Chemistry Analyser', 'Pathology Lab')");
        equipmentDb.execSQL("INSERT INTO equipment (name, room) VALUES ('ECG/EKG', 'ECG Room')");
        equipmentDb.execSQL("INSERT INTO equipment (name, room) VALUES ('Largyngoscope', 'OT')");
        equipmentDb.execSQL("INSERT INTO equipment (name, room) VALUES ('Magnifying Glasses', 'Surgery Cabinet')");
        equipmentDb.execSQL("INSERT INTO equipment (name, room) VALUES ('Bio-hazard bags', 'Store Room #1')");
        equipmentDb.execSQL("INSERT INTO equipment (name, room) VALUES ('Antibacterial Wipes', 'Store Room #2')");
        equipmentDb.execSQL("INSERT INTO equipment (name, room) VALUES ('Hand Sanitizer', 'Store Room #3')");
    }

    private void initializeDatabaseWithReservations(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("INSERT INTO reservation (title, location, details, start_time, duration, equipment_id, user_email) " +
                "VALUES ('Reservation Title1', 'Laboratory 2', 'no details provised', '1564639200', '120', '1', 'test@yahoo.com')");
        sqLiteDatabase.execSQL("INSERT INTO reservation (title, location, details, start_time, duration, equipment_id, user_email) " +
                "VALUES ('Reservation Title2', 'Laboratory 32', 'consultations', '1564650000', '180', '1', 'test@gmail.com')");
        sqLiteDatabase.execSQL("INSERT INTO reservation (title, location, details, start_time, duration, equipment_id, user_email) " +
                "VALUES ('Reservation Title3', 'Chemical Laboratory', 'details later', '1272509157', '2', '6', 'mbara@yahoo.com')");
    }

    private void initializeDatabaseWithComments(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("INSERT INTO comment (author_id, author_name, equipment_id, content, created_at) " +
                "VALUES ('1', 'test', '1', 'This equipment is restarting very hard sometimes', '1564843513000')");
        sqLiteDatabase.execSQL("INSERT INTO comment (author_id, author_name, equipment_id, content, created_at) " +
                "VALUES ('1', 'test', '2', 'It is working perfectly', '1564639200')");
        sqLiteDatabase.execSQL("INSERT INTO comment (author_id, author_name, equipment_id, content, created_at) " +
                "VALUES ('2', 'test1', '1', 'very good acquisition', '1564843679000')");
        sqLiteDatabase.execSQL("INSERT INTO comment (author_id, author_name, equipment_id, content, created_at) " +
                "VALUES ('3', 'madalina', '5', 'everything fine', '1563170400')");
    }

    /**
     * Insert values into 'user' table when the application startup
     * with few medical equipments
     *
     * @param db an object of type {@code SQLiteDatabase}
     */
    private void initializeDatabaseWithUsers(SQLiteDatabase db) {
        db.execSQL("INSERT INTO user (user_name, user_email, user_password) VALUES ('test', 'test@yahoo.com', 'test')");
        db.execSQL("INSERT INTO user (user_name, user_email, user_password) VALUES ('test1', 'test@gmail.com', 'test1')");
        db.execSQL("INSERT INTO user (user_name, user_email, user_password) VALUES ('madalina', 'mbara@yahoo.com', 'Passw0rd')");
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

    // Getting one user by id
    public User findUserById(final String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_USER, new String[]{DatabaseHelper.COLUMN_USER_ID,
                        DatabaseHelper.COLUMN_USER_NAME, DatabaseHelper.COLUMN_USER_EMAIL}, DatabaseHelper.COLUMN_USER_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        User user = null;
        if (cursor != null) {
            cursor.moveToFirst();
            user = new User(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), cursor.getString(2));
        }
        return user;
    }

    // Getting one user by id
    public User findUserByEmail(final String userEmail) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_USER, new String[]{DatabaseHelper.COLUMN_USER_ID,
                        DatabaseHelper.COLUMN_USER_NAME, DatabaseHelper.COLUMN_USER_EMAIL}, DatabaseHelper.COLUMN_USER_EMAIL + "=?",
                new String[]{userEmail}, null, null, null, null);
        User user = null;
        if (cursor != null) {
            cursor.moveToFirst();
            user = new User(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), cursor.getString(2));
        }
        return user;
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
