package com.hfad.reminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by DEVANG on 7/17/2017.
 */

public class ReminderDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "remindroid";
    private static final int DB_VERSION = 3;
    public static final String TABLE_BDAY = "BIRTHDAY";
    public SQLiteDatabase dbb;

    ReminderDatabaseHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
        dbb = getReadableDatabase();
        Log.d("Database","DB helper created");
    }

    public void onCreate(SQLiteDatabase db){

        db.execSQL("CREATE TABLE BIRTHDAY (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "FIRST_NAME TEXT,"
                +" LAST_NAME TEXT,"
                +"DATE_TIME LONG);");
        Log.d("Database","DB created");
        Log.d("Check","check db created");
    }

    public static void insertBdayDetails(SQLiteDatabase db,String firstName,
                                    String lastName,long dateTime){
        ContentValues bDayValues = new ContentValues();
        bDayValues.put("FIRST_NAME",firstName);
        bDayValues.put("LAST_NAME",lastName);
        bDayValues.put("DATE_TIME",dateTime);
        db.insert("BIRTHDAY",null,bDayValues);
        Log.d("Database ","Inserted"+firstName+lastName+dateTime);
    }
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int neewVersion){
        Log.d("Check","Upgrade called");
    }


}
