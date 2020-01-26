package com.example.ruzana;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBhelper extends SQLiteOpenHelper {


    public static final String DB_NAME = "Ruazana";
    public static final int DB_VER = 1;
    public static final String DB_TABLE = "Task";
    public static final String DB_TASK_NAME = "TaskName";


    public DBhelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createQuery = String.format("CREATE TABLE %s(ID INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL)", DB_TABLE, DB_TASK_NAME);
        db.execSQL(createQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String dropQuery = String.format("DELETE TABLE IF EXISTS %s", DB_TABLE);
        db.execSQL(dropQuery);
        onCreate(db);
    }

    public void inesrtTask(String task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DB_TASK_NAME, task);
        db.insertWithOnConflict(DB_TABLE, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void deleteTask(String task) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_TABLE, DB_TASK_NAME + " = ? ", new String[]{task});
        db.close();
    }

    public ArrayList<String> getTaskList() {
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_TABLE, new String[]{DB_TASK_NAME}, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(DB_TASK_NAME);
            taskList.add(cursor.getString(index));
        }
        cursor.close();
        db.close();
        return taskList;


    }
}