package com.example.android.myapplication.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.android.myapplication.Models.PrintPojo;
import com.example.android.myapplication.Models.UsersPojo;

import java.util.ArrayList;
import java.util.List;


public class HotelDatabase implements HotelDbConstants {

    private String TAG = HotelDatabase.class.getSimpleName();

    private Context mContext = null;
    private DbHelpher mDBHelper = null;
    private SQLiteDatabase mSQLiteDatabase = null;

    public HotelDatabase(Context ctx) {
        this.mContext = ctx;
        mDBHelper = new DbHelpher(mContext);
    }

    public HotelDatabase OpenCon() {
        mSQLiteDatabase = mDBHelper.getWritableDatabase();
        return this;
    }

    public void CloseCon() {
        mSQLiteDatabase.close();
    }


    /**
     * Add Single Record
     *
     * @param uname
     * @param date
     * @param count
     */

    public void InsertRecord(String uname, String date, String count) {

        try {

            OpenCon();

            ContentValues mValues = new ContentValues();

            mValues.put(NAME, uname);
            mValues.put(PRINT_DATE, date);
            mValues.put(COUNT_NUMBER, count);

            mSQLiteDatabase.insert(TABLE_HOTEL, null, mValues);

            CloseCon();

        } catch (Exception ex) {
            Log.v(TAG + " - - Insert Data DB Err", ex.toString());
        }
    }


    /**
     * @return All The Available Data List
     */
    public List<PrintPojo> GetAllCounts() {

        List<PrintPojo> contactList = new ArrayList<PrintPojo>();

        try {

            OpenCon();
            Cursor cursor = mSQLiteDatabase.rawQuery(SELECT_ALL_COUNT, null);

            if (cursor.moveToFirst()) {

                do {
                    contactList.add(new PrintPojo(

                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3)));

                } while (cursor.moveToNext());
            }
            return contactList;
        } catch (Exception ex) {
            Log.v(TAG + " - - GetAllUser DB Err", ex.toString());
        }

        return contactList;
    }

    /**
     * @return All The Available Data List - DateWise
     */
    public List<PrintPojo> GetAllCountDateWise(String _fromDate, String _toDate) {

        String mQueryTemp = SELECT_ALL_COUNT_DATE_WISE.replace("$from", _fromDate);

        String mQuery = mQueryTemp.replace("$to", _toDate);

        List<PrintPojo> contactList = new ArrayList<PrintPojo>();

        try {

            OpenCon();
            Cursor cursor = mSQLiteDatabase.rawQuery(mQuery, null);

            if (cursor.moveToFirst()) {

                do {
                    contactList.add(new PrintPojo(

                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3)));

                } while (cursor.moveToNext());
            }
            return contactList;
        } catch (Exception ex) {
            Log.v(TAG + " - - GetAllUser DB Err", ex.toString());
        }

        return contactList;
    }

    /**
     * Add Single User
     *
     * @param uname
     * @param passwd
     * @param mobile
     */

    public void InsertSingleUser(String uname, String passwd, String mobile) {

        try {

            OpenCon();

            ContentValues mValues = new ContentValues();

            mValues.put(NAME, uname);
            mValues.put(PRINT_DATE, passwd);
            mValues.put(COUNT_NUMBER, mobile);

            mSQLiteDatabase.insert(TABLE_USERS, null, mValues);

            CloseCon();

        } catch (Exception ex) {
            Log.v(TAG + " - - Insert User DB Err", ex.toString());
        }
    }

    /**
     * @return Single User Details
     */
    public List<UsersPojo> GetSingleUser(String _name, String _passwd) {

        String mQueryTemp = SELECT_SINGLE_USER_DETAILS.replace("$name", _name);

        String mQuery = mQueryTemp.replace("$pass", _passwd);

        List<UsersPojo> contactList = new ArrayList<UsersPojo>();

        try {

            OpenCon();
            Cursor cursor = mSQLiteDatabase.rawQuery(mQuery, null);

            if (cursor.moveToFirst()) {

                do {
                    contactList.add(new UsersPojo(
                            cursor.getString(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3)));

                } while (cursor.moveToNext());
            }
            return contactList;
        } catch (Exception ex) {
            Log.v(TAG + " - - GetAllUser DB Err", ex.toString());
        }

        return contactList;
    }


}