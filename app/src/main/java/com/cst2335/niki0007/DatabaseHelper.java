package com.cst2335.niki0007;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "Chat";
    public static final String DATABASE_NAME = "ChatDB";
    public static final int VERSION_NUMBER = 4;
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_MESSAGE = "message";
    public static final String COLUMN_MESSAGE_TYPE = "chatType";

    public DatabaseHelper(Activity ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUMBER);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + TABLE_NAME+ "(" +COLUMN_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_MESSAGE + " TEXT, " +COLUMN_MESSAGE_TYPE+ " INTEGER)");
    }

    public void onUpgrade(SQLiteDatabase sqlLiteDatabase, int oldVersion, int newVersion){
        Log.e("Database Upgraded", +oldVersion+" -> "+newVersion);
        sqlLiteDatabase.execSQL("Drop Table " +TABLE_NAME);
        onCreate(sqlLiteDatabase);
    }

    public void onDownGrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion){
        Log.e("Database Downgraded", +newVersion+ " -> "+oldVersion);
        sqLiteDatabase.execSQL("Drop Table " +TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}