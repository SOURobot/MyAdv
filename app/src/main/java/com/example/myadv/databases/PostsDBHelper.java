package com.example.myadv.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PostsDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "posts.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_POSTS = "posts";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_DATE= "date";
    public static final String COLUMN_HEADER = "header";
    public static final String COLUMN_INFO = "info";
    public static final String COLUMN_CONTACT = "contact";
    public static final String COLUMN_CATEGORY = "category";

    private static final String DATABASE_CREATE = "create table "
            + TABLE_POSTS + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_USERNAME + " text not null, "
            + COLUMN_DATE + " text not null, "
            + COLUMN_HEADER + " text not null, "
            + COLUMN_INFO + " text not null, "
            + COLUMN_CONTACT + " text not null, "
            + COLUMN_CATEGORY + " text not null);";


    public PostsDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POSTS);
        onCreate(db);
    }

    public long addPost(String username, String date, String header, String info, String contact, String category) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_HEADER, header);
        values.put(COLUMN_INFO, info);
        values.put(COLUMN_CONTACT, contact);
        values.put(COLUMN_CATEGORY, category);

        long result = db.insert(TABLE_POSTS, null, values);
        db.close();
        return result;
    }

    public Cursor getUserPosts(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {
                COLUMN_ID,
                COLUMN_HEADER,
                COLUMN_CATEGORY,
                COLUMN_DATE,
                COLUMN_USERNAME,
                COLUMN_INFO,
                COLUMN_CONTACT
        };

        return db.query(
                TABLE_POSTS,
                columns,
                COLUMN_USERNAME + " = ?",
                new String[]{username},
                null,
                null,
                COLUMN_DATE + " DESC"
        );
    }

    public Cursor getAllPosts() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_POSTS,
                new String[]{COLUMN_ID,
                        COLUMN_HEADER,
                        COLUMN_CATEGORY,
                        COLUMN_DATE,
                        COLUMN_USERNAME,
                        COLUMN_INFO,
                        COLUMN_CONTACT},
                null, null, null, null,
                COLUMN_DATE + " DESC");
    }
}
