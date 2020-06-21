package com.example.studentmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DAOQuery {
    SQLiteDatabase db;
    CreateDB createDB;

    public DAOQuery(Context context) {
        createDB = new CreateDB(context);
    }

    public void open() {
        db = createDB.getWritableDatabase();
    }
    public void close() {
        createDB.close();
    }

    public boolean addStudent(Student s) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDB.S_SID, s.getSid());
        contentValues.put(CreateDB.S_NAME, s.getName());
        contentValues.put(CreateDB.S_EMAIL, s.getEmail());
        contentValues.put(CreateDB.S_DOB, s.getDob());
        contentValues.put(CreateDB.S_ADDR, s.getAddress());
        long rs = db.insert(CreateDB.TABLE_NAME, null, contentValues);
        if (rs != -1) {
            return true;
        } else return false;
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        db.beginTransaction();
        String sql = "SELECT * FROM " + CreateDB.TABLE_NAME;
        Cursor cursor = db.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Student s = new Student();
            s.setId(cursor.getInt(cursor.getColumnIndex(CreateDB.S_ID)));
            s.setSid(cursor.getInt(cursor.getColumnIndex(CreateDB.S_SID)));
            s.setName(cursor.getString(cursor.getColumnIndex(CreateDB.S_NAME)));
            s.setEmail(cursor.getString(cursor.getColumnIndex(CreateDB.S_EMAIL)));
            s.setDob(cursor.getString(cursor.getColumnIndex(CreateDB.S_DOB)));
            s.setAddress(cursor.getString(cursor.getColumnIndex(CreateDB.S_ADDR)));
            students.add(s);
            cursor.moveToNext();
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        return students;
    }

    public boolean deleteStudent(Student s) {
        db.beginTransaction();
        int rs = db.delete(CreateDB.TABLE_NAME,
                CreateDB.S_ID + " = " + s.getId(), null);
        db.setTransactionSuccessful();
        db.endTransaction();
        if (rs != 0) {
            return true;

        } else return false;
    }

    public boolean updateStudent(Student s) {
        db.beginTransaction();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDB.S_SID, s.getSid());
        contentValues.put(CreateDB.S_NAME, s.getName());
        contentValues.put(CreateDB.S_EMAIL, s.getEmail());
        contentValues.put(CreateDB.S_DOB, s.getDob());
        contentValues.put(CreateDB.S_ADDR, s.getAddress());
        int rs = db.update(CreateDB.TABLE_NAME, contentValues,
                CreateDB.S_ID + " = " + s.getId(), null);
        db.setTransactionSuccessful();
        db.endTransaction();
        if (rs != 0) return true;
        else return false;
    }
}
