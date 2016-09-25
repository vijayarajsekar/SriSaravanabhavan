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

public class LoginActivity extends Activity {

    private String TAG = LoginActivity.class.getSimpleName();

    private EditText mloginName, mloginPaswd;

    private String mstrName, mstrPaswd;

    private Button mLogin;

    private Context mContext;

    private TextView mSignUpText;

    private LinearLayout mLoginLayout, mSignUpLayout;

    private List<UsersPojo> mUsersPojo;

    private HotelDatabase mHotelDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login_signup);

        mContext = this;

        mHotelDatabase = new HotelDatabase(mContext);

        mloginName = (EditText) findViewById(R.id.editText1);
        mloginPaswd = (EditText) findViewById(R.id.editText2);

        mLogin = (Button) findViewById(R.id.btn_login);

        mSignUpText = (TextView) findViewById(R.id.text_signup);

        mLoginLayout = (LinearLayout) findViewById(R.id.login_layout);
        mLoginLayout.setVisibility(View.VISIBLE);

        mSignUpLayout = (LinearLayout) findViewById(R.id.signup_layout);
        mSignUpLayout.setVisibility(View.GONE);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mstrName = mloginName.getText().toString();
                mstrPaswd = mloginPaswd.getText().toString();

                if (mstrName != null && mstrName.toString().length() != 0) {

                    mUsersPojo = mHotelDatabase.GetSingleUser(mstrName, mstrPaswd);

                    if (mUsersPojo != null && mUsersPojo.size() != 0) {
                        new AppPreferences().setIsLogin(true);
                        new AppPreferences().setName(mUsersPojo.get(0).getName());
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    } else {
                        Toast.makeText(LoginActivity.this, "Invalid Name / Password", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(LoginActivity.this, "Invalid Details", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mSignUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }
}