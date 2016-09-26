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
import com.example.android.myapplication.R;

import java.sql.Timestamp;

public class SignUpActivity extends Activity {

    private String TAG = SignUpActivity.class.getSimpleName();

    private EditText msignupName, msignupPaswd, msignupMobileNumber;

    private String mstrName, mstrPaswd, mstrMobileNumber, mTimeStamp;

    private Button mSignUp;

    private Context mContext;

    private TextView mLoginText;

    private LinearLayout mLoginLayout, mSignUpLayout;

    private HotelDatabase mHotelDatabase;

    private java.util.Date mDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login_signup);

        mContext = this;

        mHotelDatabase = new HotelDatabase(mContext);

        mDate = new java.util.Date();

        msignupName = (EditText) findViewById(R.id.editText3);
        msignupPaswd = (EditText) findViewById(R.id.editText4);
        msignupMobileNumber = (EditText) findViewById(R.id.editText5);

        mSignUp = (Button) findViewById(R.id.btn_signup);

        mLoginText = (TextView) findViewById(R.id.text_login);

        mLoginLayout = (LinearLayout) findViewById(R.id.login_layout);
        mLoginLayout.setVisibility(View.GONE);

        mSignUpLayout = (LinearLayout) findViewById(R.id.signup_layout);
        mSignUpLayout.setVisibility(View.VISIBLE);

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(SignUpActivity.this, MainActivity.class));

                mstrName = msignupName.getText().toString();
                mstrPaswd = msignupPaswd.getText().toString();
                mstrMobileNumber = msignupMobileNumber.getText().toString();
                mTimeStamp = String.valueOf(new Timestamp(mDate.getTime()));

                if (mstrName != null && mstrName.toString().length() < 5 && msignupMobileNumber.toString().length() < 5) {
                    Toast.makeText(SignUpActivity.this, " name & password must 6 character", Toast.LENGTH_SHORT).show();
                } else if (mstrName != null && mstrName.toString().length() != 0 && msignupMobileNumber.toString().length() != 0) {

                    mHotelDatabase.InsertSingleUser(mstrName, mstrPaswd, mstrMobileNumber, mTimeStamp);

                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));

                } else {
                    Toast.makeText(SignUpActivity.this, "Enter Valid Details", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mLoginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
    }
}