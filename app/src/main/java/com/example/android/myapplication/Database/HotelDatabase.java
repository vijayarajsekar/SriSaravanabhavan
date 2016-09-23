package com.example.android.myapplication.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


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

            mSQLiteDatabase.insert(HOTEL_TABLE, null, mValues);

            CloseCon();

        } catch (Exception ex) {
            Log.v(TAG + " - - Insert Data DB Err", ex.toString());
        }
    }

//
//    /**
//     * @return All The Available Users List
//     */
//    public List<VoiceMail> GetAllMail() {
//
//        List<VoiceMail> contactList = new ArrayList<VoiceMail>();
//
//        try {
//
//            OpenCon();
//            Cursor cursor = mSQLiteDatabase.rawQuery(SELECT_ALL_MAIL, null);
//
//            if (cursor.moveToFirst()) {
//
//                do {
//                    contactList.add(new VoiceMail(
//
//                            cursor.getString(1),
//                            cursor.getString(2),
//                            cursor.getString(3),
//                            cursor.getString(4),
//                            cursor.getString(5),
//                            Integer.parseInt(cursor.getString(6))));
//
//                } while (cursor.moveToNext());
//            }
//            return contactList;
//        } catch (Exception ex) {
//            Logger.Print(TAG + " - - GetAllUser DB Err", ex.toString());
//        }
//
//        return contactList;
//    }

    /**
     * @return All Online Users Count
     */
    public int GetVoiceMailCount(String uniqid) {

        try {

            OpenCon();

            String data = "";

            Cursor cursor = mSQLiteDatabase.rawQuery(SELECT_MAIL_COUNT.toString(), null);

            if (cursor.moveToFirst()) {

                do {

                    data = cursor.getString(cursor.getColumnIndex("Count"));

                } while (cursor.moveToNext());
            }

            return Integer.parseInt(data);

        } catch (Exception ex) {
            Log.v(TAG + " - - GetAllUser DB Err", ex.toString());
        }

        return -1;
    }
}