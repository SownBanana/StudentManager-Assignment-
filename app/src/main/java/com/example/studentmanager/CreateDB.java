package com.example.studentmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class CreateDB extends SQLiteOpenHelper {
    public static final String DB_NAME ="StudentManager";
    public static final int DB_VERSION =1;
    public static final String TABLE_NAME ="student";
    public static final String S_ID="id";
    public static final String S_SID="sid";
    public static final String S_NAME="name";
    public static final String S_DOB="dob";
    public static final String S_EMAIL="email";
    public static final String S_ADDR="address";
    private SQLiteDatabase db;

    public CreateDB(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db=db;
        String sql ="CREATE TABLE "+TABLE_NAME+" ( "+S_ID+
                " INTEGER PRIMARY KEY AUTOINCREMENT,"+S_SID + " INTEGER, "+S_NAME +" TEXT, " +
                S_DOB +" TEXT, " +
                S_EMAIL +" TEXT, " +
                S_ADDR +" TEXT" +
                ");";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME );
        onCreate(db);
    }

}
