package com.bignerdranch.android.trainerbook.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bignerdranch.android.trainerbook.database.CustomerDbSchema.CustomerTable;

public class CustomerBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "customerBase.db";

    public CustomerBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + CustomerTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                CustomerTable.Cols.UUID + ", " +
                CustomerTable.Cols.NAME + ", " +
                CustomerTable.Cols.DATE + ", " +
                CustomerTable.Cols.BILLING +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
