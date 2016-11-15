package com.example.android.myapplication.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.android.myapplication.Models.PrintPojo;
import com.example.android.myapplication.Models.ReturnPojo;
import com.example.android.myapplication.Models.UsersPojo;
import com.example.android.myapplication.Preferences.AppPreferences;
import com.example.android.myapplication.R;

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

    public void InsertRecord(String uname, String date, String count, String foodtype, String tokenId) {

        try {

            OpenCon();

            ContentValues mValues = new ContentValues();

            mValues.put(NAME, uname);
            mValues.put(PRINT_DATE, date);
            mValues.put(COUNT_NUMBER, count);
            mValues.put(FOOD_TYPE, foodtype);
            mValues.put(RET_TOKEN_ID, tokenId);
            mValues.put(RET_COUNT, "0");

            mSQLiteDatabase.insert(TABLE_HOTEL, null, mValues);

            CloseCon();

        } catch (Exception ex) {
            Log.v(TAG + " - - Insert Data DB Err", ex.toString());
        }
    }

    /**
     * Update ReturnCount
     *
     * @param Id
     * @param Count
     */

    public int UpdateReturnToken(String Id, int Count) {

        String[] xx = GetAllCountById(Id).split(",");

        if (xx == null)
            return -1;

        int x = 0;
        int y = Integer.parseInt(xx[0]);
        String mFoodType = xx[1];
        int qqq = -1;

        try {

            OpenCon();

            ContentValues mValues = new ContentValues();

            if (y != 0)
                x = y - Count;

            if (x >= 0) {

                mValues.put(COUNT_NUMBER, x);
                mValues.put(RET_COUNT, String.valueOf(Count));

                if (mFoodType.toString().equals("P")) {
                    new AppPreferences().setPrintParcelCount(new AppPreferences().getPrintParcelCount() - Count);
                } else {
                    new AppPreferences().setPrintCount(new AppPreferences().getPrintCount() - Count);
                }

            } else {
                return -1;
            }

            qqq = mSQLiteDatabase.update(TABLE_HOTEL, mValues, RET_TOKEN_ID + " = ?",
                    new String[]{String.valueOf(Id)});

            System.out.println("~ ~ ~ ~ ~ qqq " + qqq);

            CloseCon();

        } catch (Exception ex) {
            Log.v(TAG + " - - Insert User DB Err", ex.toString());
        }

        return qqq;
    }

    /**
     * @return Count By Id
     */
    private String GetAllCountById(String _id) {

        String mQuery = SELECT_RET_COUNT_DATE_WISE.replace("$tokid", _id);
        String countRes = null;
        try {

            OpenCon();
            Cursor cursor = mSQLiteDatabase.rawQuery(mQuery, null);

            if (cursor.moveToFirst()) {

                do {
                    countRes = cursor.getString(0) + "," + cursor.getString(1);
                } while (cursor.moveToNext());
            }

        } catch (Exception ex) {
            Log.v(TAG + " - - GetAllUser DB Err", ex.toString());
        }

        return countRes;
    }

    /**
     * @return All The Available Data List - DateWise
     */
    public List<PrintPojo> GetAllCountDateWise(String _fromDate, String _toDate, String _foodType) {

        String mQueryTemp1 = SELECT_ALL_COUNT_DATE_WISE.replace("$from", _fromDate);

        String mQueryTemp2 = mQueryTemp1.replace("$to", _toDate);

        String mQuery = mQueryTemp2.replace("$type", _foodType);

        List<PrintPojo> contactList = new ArrayList<PrintPojo>();

        try {

            OpenCon();
            Cursor cursor = mSQLiteDatabase.rawQuery(mQuery, null);

            if (cursor.moveToFirst()) {

                do {
                    contactList.add(new PrintPojo(

                            cursor.getString(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3)));

                } while (cursor.moveToNext());
            }

        } catch (Exception ex) {
            Log.v(TAG + " - - GetAllUser DB Err", ex.toString());
        }

        return contactList;
    }

    /**
     * @return All The Available Data List - DateWise
     */
    public String GetAllLastInsertedData() {

        String mQuery = SELECT_LAST_INSERT_DATA;

        String mRes = null;

        try {

            OpenCon();
            Cursor cursor = mSQLiteDatabase.rawQuery(mQuery, null);

            if (cursor.moveToFirst()) {

                do {
                    mRes = cursor.getString(3) + "," + cursor.getString(4) + "," + cursor.getString(5) /*+ "," + cursor.getString(3)
                            + "," + cursor.getString(4) + "," + cursor.getString(5)*/;
                } while (cursor.moveToNext());
            }

        } catch (Exception ex) {
            Log.v(TAG + " - - GetAllUser DB Err", ex.toString());
        }

        return mRes;
    }


    /**
     * Add Single User
     *
     * @param uname
     * @param passwd
     * @param mobile
     * @param timeStamp
     */

    public void InsertSingleUser(String uname, String passwd, String mobile, String timeStamp) {

        try {

            OpenCon();

            ContentValues mValues = new ContentValues();

            mValues.put(NAME, uname);
            mValues.put(PASSWORD, passwd);
            mValues.put(MOBILE, mobile);
            mValues.put(PRINT_DATE, timeStamp);

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

    /**
     * Update Single User Password
     *
     * @param name
     * @param passwd
     */

    public void UpdatePassword(String name, String passwd) {

        try {

            OpenCon();

            ContentValues mValues = new ContentValues();

            mValues.put(PASSWORD, passwd);

            mSQLiteDatabase.update(TABLE_USERS, mValues, NAME + " = ?",
                    new String[]{String.valueOf(name)});

            CloseCon();

        } catch (Exception ex) {
            Log.v(TAG + " - - Insert User DB Err", ex.toString());
        }
    }
}