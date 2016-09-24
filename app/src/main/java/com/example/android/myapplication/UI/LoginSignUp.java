package com.example.android.myapplication.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.myapplication.Preferences.AppPreferences;
import com.example.android.myapplication.R;

public class LoginSignUp extends Activity {

    private String TAG = LoginSignUp.class.getSimpleName();

    private EditText mEmail, mPaswd, mMobileNumber;

    private Button mLoginSignUp;

    private TextView mSignUpText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login_signup);

        mEmail = (EditText) findViewById(R.id.editText1);
        mPaswd = (EditText) findViewById(R.id.editText2);
        mMobileNumber = (EditText) findViewById(R.id.editText3);

        mLoginSignUp = (Button) findViewById(R.id.btn_loginsignup);

        mSignUpText = (TextView) findViewById(R.id.text_signup);


        mLoginSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginSignUp.this, MainActivity.class));
            }
        });
    }
}
