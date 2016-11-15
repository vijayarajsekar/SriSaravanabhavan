package com.example.android.myapplication.Models;

/**
 * Created by android on 21/9/16.
 */
public class PrintPojo {

    private String mSno;
    private String mDateWise;
    private String mTotalCount;
    private String mRetCount;

    public PrintPojo(String _Sno, String _DateWise, String _TotalCount, String _RetCount) {

        this.mSno = _Sno;
        this.mDateWise = _DateWise;
        this.mTotalCount = _TotalCount;
        this.mRetCount=_RetCount;
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

    public String getRetCount() {
        return mRetCount;
    }
}
