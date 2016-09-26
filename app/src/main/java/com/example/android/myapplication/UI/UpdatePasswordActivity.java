package com.example.android.myapplication.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.myapplication.Database.HotelDatabase;
import com.example.android.myapplication.Models.UsersPojo;
import com.example.android.myapplication.Preferences.AppPreferences;
import com.example.android.myapplication.R;

import java.util.List;

public class UpdatePasswordActivity extends Activity {

    private String TAG = UpdatePasswordActivity.class.getSimpleName();

    private EditText mOldPassword, mNewPassword, mReNewPassword;

    private String mstrPaswd, mstrPaswdNew, mstrPaswdReNew;

    private HotelDatabase mHotelDatabase;

    private Button mUpdate;

    private Context mContext;

    private List<UsersPojo> mUsersPojo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_usersettings);

        mContext = this;

        mHotelDatabase = new HotelDatabase(mContext);

        mOldPassword = (EditText) findViewById(R.id.editText1);
        mNewPassword = (EditText) findViewById(R.id.editText2);
        mReNewPassword = (EditText) findViewById(R.id.editText3);

        mUpdate = (Button) findViewById(R.id.btnupdate);

        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mstrPaswd = mOldPassword.getText().toString();
                mstrPaswdNew = mNewPassword.getText().toString();
                mstrPaswdReNew = mReNewPassword.getText().toString();

                mUsersPojo = mHotelDatabase.GetSingleUser(new AppPreferences().getName(), mstrPaswd);

                if (mUsersPojo.size() != 0) {

                    if (mstrPaswd == null && mstrPaswd.toString().length() == 0) {
                        Toast.makeText(mContext, "Enter valid old password", Toast.LENGTH_SHORT).show();
                    } else if (mstrPaswdNew == null && mstrPaswdNew.toString().length() == 0) {
                        Toast.makeText(mContext, "Enter valid new password", Toast.LENGTH_SHORT).show();
                    } else if (mstrPaswdReNew == null && mstrPaswdReNew.toString().length() == 0) {
                        Toast.makeText(mContext, "Enter valid new password", Toast.LENGTH_SHORT).show();
                    } else if (!mstrPaswdReNew.toString().equals(mstrPaswdNew)) {
                        Toast.makeText(mContext, "Password mis matching", Toast.LENGTH_SHORT).show();
                    } else if (mstrPaswdNew == null && mstrPaswdNew.toString().length() < 6) {
                        Toast.makeText(mContext, "password must 6 character", Toast.LENGTH_SHORT).show();
                    } else if (mstrPaswdReNew == null && mstrPaswdReNew.toString().length() == 0) {
                        Toast.makeText(mContext, "password must 6 character", Toast.LENGTH_SHORT).show();
                    } else {
                        mHotelDatabase.UpdatePassword(new AppPreferences().getName(), mstrPaswdNew);
                        finish();
                    }

                } else {
                    Toast.makeText(mContext, "Invalid old password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}