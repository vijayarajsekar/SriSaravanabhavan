package com.example.android.myapplication.Models;

/**
 * Created by android on 21/9/16.
 */
public class PrintPojo {

    private String mSno;
    private String mDateWise;
    private String mTotalCount;


    public PrintPojo(String _Sno, String _DateWise, String _TotalCount) {

        this.mSno = _Sno;
        this.mDateWise = _DateWise;
        this.mTotalCount = _TotalCount;

    }

    public String getSno() {
        return mSno;
    }

    public String getDateWise() {
        return mDateWise;
    }

    public String getTotalCount() {
        return mTotalCount;
    }
}
