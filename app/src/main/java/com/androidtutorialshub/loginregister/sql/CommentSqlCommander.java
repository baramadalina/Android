package com.androidtutorialshub.loginregister.sql;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.androidtutorialshub.loginregister.model.Comment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CommentSqlCommander {
    
    private SQLiteOpenHelper sqLiteOpenHelper;

    public CommentSqlCommander(SQLiteOpenHelper sqLiteOpenHelper) {
        this.sqLiteOpenHelper = sqLiteOpenHelper;
    }

    /**
     * Adding new comment to a specific equipment
     *
     * @param comment an object of type {@code Comment}
     */
    public void addComment(Comment comment) {
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        Random r = new Random();
/*            int commentId = r.nextInt(10000 - 1) + 1;
            values.put(DatabaseHelper.COMMENT_ID, commentId);*/
        values.put(DatabaseHelper.COMMENT_AUTHOR_ID, comment.getAuthorID());
        values.put(DatabaseHelper.COMMENT_AUTHOR_NAME, comment.getAuthorName());
        values.put(DatabaseHelper.COMMENT_EQUIPMENT_ID, comment.getEquipmentID());
        values.put(DatabaseHelper.COMMENT_CONTENT, comment.getContent());
        values.put(DatabaseHelper.COMMENT_CREATED_AT, comment.getCreatedAtTimestamp());
        try {
            // Inserting Row
            db.insert(DatabaseHelper.TABLE_COMMENT, null, values);
        } catch (Exception e) {
            int commentId = r.nextInt(10000 - 1) + 1;
            values.put(DatabaseHelper.COMMENT_ID, commentId);
            db.insert(DatabaseHelper.TABLE_COMMENT, null, values);
        } finally {
            // Closing database connection
            db.close();
        }
    }

    /**
     * Getting Comments for a specific equipment
     *
     * @return comments list of object of type {@code List<Comment>}
     */
    public List<Comment> getCommentsForEquipmentId(int equipmentId) {
        List<Comment> commentList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_COMMENT + " WHERE " + DatabaseHelper.COMMENT_EQUIPMENT_ID + "=" + equipmentId;
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                final Comment comment = new Comment();
                comment.setId(Integer.parseInt(cursor.getString(0)));
                comment.setAuthorID(cursor.getInt(1));
                comment.setAuthorName(cursor.getString(2));
                comment.setEquipmentID(cursor.getInt(3));
                comment.setContent(cursor.getString(4));
                comment.setCreatedAtTimestamp(Long.parseLong(cursor.getString(5)));

                // Adding comment to list
                commentList.add(comment);
            } while (cursor.moveToNext());
        }
        db.close();
        // return comment events list
        return commentList;
    }

    // Getting the last added comment for a specific equipment
    public Comment getLastAddedComment(int equipmentId) {
        SQLiteDatabase db = sqLiteOpenHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_COMMENT, new String[]{DatabaseHelper.COMMENT_ID, DatabaseHelper.COMMENT_AUTHOR_ID,
                        DatabaseHelper.COMMENT_EQUIPMENT_ID, DatabaseHelper.COMMENT_CONTENT, DatabaseHelper.COMMENT_AUTHOR_NAME, DatabaseHelper.COMMENT_CREATED_AT},
                DatabaseHelper.COMMENT_CREATED_AT + "=?",
                new String[]{String.valueOf(equipmentId)}, null, null, null, null);
        Comment comment = null;
        if (cursor != null) {
            cursor.moveToFirst();
            // int id, int authorID, int equipmentID, String content, String authorName, Long createdAtTimestamp
            comment = new Comment(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)),
                    Integer.parseInt(cursor.getString(2)), cursor.getString(3), cursor.getString(4),
                    Long.parseLong(cursor.getString(5)));
        }
        db.close();
        return comment;
    }


    /**
     * Updating a comment
     *
     * @param comment object of type {@code Comment }
     */
    public void updateComment(Comment comment) {
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COMMENT_AUTHOR_ID, comment.getAuthorID());
        values.put(DatabaseHelper.COMMENT_AUTHOR_NAME, comment.getAuthorName());
        values.put(DatabaseHelper.COMMENT_EQUIPMENT_ID, comment.getEquipmentID());
        values.put(DatabaseHelper.COMMENT_CONTENT, comment.getContent());
        values.put(DatabaseHelper.COMMENT_CREATED_AT, comment.getCreatedAtTimestamp());
        // updating row
        db.update(DatabaseHelper.TABLE_COMMENT, values, DatabaseHelper.COMMENT_ID + " = ?",
                new String[]{String.valueOf(comment.getId())});
        db.close();
    }

    /**
     * Deleting a comment
     *
     * @param comment object of type {@code Comment }
     */
    public void deleteComment(Comment comment) {
        SQLiteDatabase db = sqLiteOpenHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_COMMENT, DatabaseHelper.COMMENT_ID + " = ?",
                new String[]{String.valueOf(comment.getId())});
        db.close();
    }


}
