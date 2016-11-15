package com.example.android.myapplication.Preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.android.myapplication.AppController;

/**
 * Created by android on 25/8/16.
 */
public class AppPreferences extends AppConstants {

    private static final String TAG = AppPreferences.class.getSimpleName();

    // Shared Preferences
    private static SharedPreferences mPreferences;

    // Editor for Shared preferences
    private SharedPreferences.Editor mEditor;

    // Context
    private Context mContext;

    // Shared pref mode
    private int PRIVATE_MODE = 0;

    private static AppPreferences instance;

    public static final synchronized AppPreferences instance() {
        if (instance == null) {
            instance = new AppPreferences();
        }
        return instance;
    }

    /**
     * Constructor
     */

    public AppPreferences() {
        this.mContext = AppController.getInstance();
        mPreferences = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        mEditor = mPreferences.edit();
    }

    public boolean getIsLogin() {
        return mPreferences.getBoolean(IS_LOGIN, false);
    }

    public void setIsLogin(boolean values) {
        mEditor.putBoolean(IS_LOGIN, values);
        mEditor.commit();
    }

    public String getName() {
        return mPreferences.getString(NAME, "");
    }

    public void setName(String values) {
        mEditor.putString(NAME, values);
        mEditor.commit();
    }

    public String getEmail() {
        return mPreferences.getString(EMAIL, "");
    }

    public void setEmail(String values) {
        mEditor.putString(EMAIL, values);
        mEditor.commit();
    }

    public String getPhone() {
        return mPreferences.getString(PHONE, "");
    }

    public void setPhone(String values) {
        mEditor.putString(PHONE, values);
        mEditor.commit();
    }

    public int getPrinterModel() {
        return mPreferences.getInt(PRIINTER_MODEL, -1);
    }

    public void setPrinterModel(int values) {
        mEditor.putInt(PRIINTER_MODEL, values);
        mEditor.commit();
    }

    public int getPrinterLang() {
        return mPreferences.getInt(PRIINTER_LANG, -1);
    }

    public void setPrinterLang(int values) {
        mEditor.putInt(PRIINTER_LANG, values);
        mEditor.commit();
    }

    public String getPrinterTarget() {
        return mPreferences.getString(PRIINTER_TARGET, "");
    }

    public void setPrinterTarget(String values) {
        mEditor.putString(PRIINTER_TARGET, values);
        mEditor.commit();
    }

    public int getPrintCount() {
        return mPreferences.getInt(PRIINT_COUNT, 0);
    }

    public void setPrintCount(int values) {
        mEditor.putInt(PRIINT_COUNT, values);
        mEditor.commit();
    }

    public int getPrintParcelCount() {
        return mPreferences.getInt(PRIINT_PARCEL_COUNT, 0);
    }

    public void setPrintParcelCount(int values) {
        mEditor.putInt(PRIINT_PARCEL_COUNT, values);
        mEditor.commit();
    }

    public int getPrintType() {
        return mPreferences.getInt(PRIINT_TYPE, -1);
    }

    public void setPrintType(int values) {
        mEditor.putInt(PRIINT_TYPE, values);
        mEditor.commit();
    }

    public int getLSerialNo() {
        return mPreferences.getInt(L_SERIAL, 0);
    }

    public void setLSerialNo(int values) {
        mEditor.putInt(L_SERIAL, values);
        mEditor.commit();
    }

    public int getPSerialNo() {
        return mPreferences.getInt(P_SERIAL, 0);
    }

    public void setPSerialNo(int values) {
        mEditor.putInt(P_SERIAL, values);
        mEditor.commit();
    }

    public boolean getDummyInsert() {
        return mPreferences.getBoolean(IS_DUMMY_INSERT, false);
    }

    public void setDummyInsert(boolean values) {
        mEditor.putBoolean(IS_DUMMY_INSERT, values);
        mEditor.commit();
    }

//    public int getRetFM() {
//        return mPreferences.getInt(RET_FM_COUNT, 0);
//    }
//
//    public void setRetFM(int values) {
//        mEditor.putInt(RET_FM_COUNT, values);
//        mEditor.commit();
//    }
//
//    public int getRetPM() {
//        return mPreferences.getInt(RET_PM_COUNT, 0);
//    }
//
//    public void setRetPM(int values) {
//        mEditor.putInt(RET_PM_COUNT, values);
//        mEditor.commit();
//    }
}