package com.example.android.myapplication.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Android on 16/8/16.
 */

class DbHelpher extends SQLiteOpenHelper implements HotelDbConstants {

    public DbHelpher(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_HOTEL_TABLE);
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_HOTEL_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_USERS_TABLE);

        onCreate(db);
    }
}
