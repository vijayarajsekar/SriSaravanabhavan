package com.example.android.myapplication.Models;

/**
 * Created by android on 21/9/16.
 */
public class UsersPojo {

    private String mId;
    private String mName;
    private String mPassed;
    private String mMobile;

    public UsersPojo(String _id, String _name, String _pass, String _mobile) {

        this.mId = _id;
        this.mName = _name;
        this.mPassed = _pass;
        this.mMobile = _mobile;
    }

    public String getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public String getPassed() {
        return mPassed;
    }

    public String getMobile() {
        return mMobile;
    }
}
