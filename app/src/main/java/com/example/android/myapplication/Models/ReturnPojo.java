package com.example.android.myapplication.Models;

/**
 * Created by android on 21/9/16.
 */
public class ReturnPojo {

    private String mRetCount;
    private String mFoodType;
    private String mDate;


    public ReturnPojo(String _mFoodTypeF, String _mDate, String _mRetCount) {

        this.mRetCount = _mRetCount;
        this.mFoodType = _mFoodTypeF;
        this.mDate = _mDate;

    }

    public String getRetCount() {
        return mRetCount;
    }

    public String getFoodType() {
        return mFoodType;
    }

    public String getDate() {
        return mDate;
    }
}
