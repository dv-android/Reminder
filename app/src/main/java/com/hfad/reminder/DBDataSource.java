package com.hfad.reminder;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by DEVANG on 7/19/2017.
 */

public class DBDataSource {
    private SQLiteDatabase database;

    private ReminderDatabaseHelper dbHelper;
    private ArrayList<BdayListItem> bdayListItems = new ArrayList<BdayListItem>();

    private String[] allColumns = {"FIRST_NAME","LAST_NAME","DATE_TIME"};

    public DBDataSource(Context context)
    {
        dbHelper = new ReminderDatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public BdayListItem cursorToBdayListItem(Cursor cursor){
        BdayListItem bd = new BdayListItem(R.mipmap.ic_launcher,cursor.getString(0),cursor.getString(1),cursor.getLong(2));
        return bd;
    }


        public ArrayList<BdayListItem> getAllBdayRows(){

            Cursor cursor = database.query(ReminderDatabaseHelper.TABLE_BDAY,
                    allColumns, null, null, null, null, null);

            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                BdayListItem bd = cursorToBdayListItem(cursor);
                bdayListItems.add(bd);
                cursor.moveToNext();
            }
            cursor.close();

            return  bdayListItems;
    }
}
