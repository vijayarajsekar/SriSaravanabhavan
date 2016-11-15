/*
 * Copyright (C) 2013 Andreas Stuetz <andreas.stuetz@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.myapplication.UI;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.method.PasswordTransformationMethod;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.example.android.myapplication.Adapter.PagerAdapter;
import com.example.android.myapplication.Database.HotelDatabase;
import com.example.android.myapplication.Fragments.ScreenLunch;
import com.example.android.myapplication.Interfaces.ReprintInterface;
import com.example.android.myapplication.Models.ReturnPojo;
import com.example.android.myapplication.Models.UsersPojo;
import com.example.android.myapplication.Preferences.AppPreferences;
import com.example.android.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends FragmentActivity {

    private String TAG = MainActivity.class.getSimpleName();

    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private PagerAdapter mPagerAdapter;

    private AppPreferences mPreferences;

    private Context mContext;

    private String mTodayDate;

    private LinearLayout mSideMenuView;
    private TextView mItem1, mItem2, mItem3, mItem4, mItem5, mItem6;

    private boolean mIsVisible = false;

    private Animation mAnimationIn, mAnimationOut;

    private String mStrPasscode, mStrCount, mTokId;

    private AlertDialog.Builder builder;
    private AlertDialog mAlertDialog;

    private Calendar calendar;

    private SimpleDateFormat mSimpleDateFormat;

    private List<ReturnPojo> mReturnCountList;

    private String mFoodType;

    private EditText mReturnCount;
    private EditText mTokenId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_main);

        mContext = MainActivity.this;

        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#1CA182")));
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        pager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager());

        mSideMenuView = (LinearLayout) findViewById(R.id.menuview);

        mPreferences = new AppPreferences();

        mAnimationIn = AnimationUtils.loadAnimation(mContext, R.anim.go_right_in);
        mAnimationOut = AnimationUtils.loadAnimation(mContext, R.anim.go_right_out);

        mItem1 = (TextView) findViewById(R.id.menuview_item1);
        mItem2 = (TextView) findViewById(R.id.menuview_item2);
        mItem3 = (TextView) findViewById(R.id.menuview_item3);
        mItem4 = (TextView) findViewById(R.id.menuview_item4);
        mItem5 = (TextView) findViewById(R.id.menuview_item5);
        mItem6 = (TextView) findViewById(R.id.menuview_item6);

        pager.setAdapter(mPagerAdapter);

        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);

        tabs.setViewPager(pager);

        calendar = Calendar.getInstance();
        mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        /**
         * Set Logged in User Name
         */
        ((TextView) findViewById(R.id.text_username)).setText("Login - " + new AppPreferences().getName());

        mItem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mSideMenuView.setVisibility(View.GONE);
                mSideMenuView.startAnimation(mAnimationOut);
                mIsVisible = false;

                final EditText mPasscode;
                LinearLayout.LayoutParams mLayoutParams = null;

                mPasscode = new EditText(mContext);
                mPasscode.setHint(R.string.passcode);
                mPasscode.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD);
                mPasscode.setTransformationMethod(PasswordTransformationMethod.getInstance());

                mLayoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                mPasscode.setLayoutParams(mLayoutParams);

                builder = new AlertDialog.Builder(mContext, R.style.AppCompatAlertDialogStyle);
                builder.setTitle("Pin Verification");
                builder.setView(mPasscode, 50, 50, 50, 50);
                builder.setIcon(R.drawable.icon_lock);
                builder.setCancelable(false);

                mAlertDialog = builder.create();

                mAlertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        mStrPasscode = mPasscode.getText().toString();

                        String mTemp = "sri@" + new AppPreferences().getName();

                        if (mStrPasscode != null && mStrPasscode.equals(mTemp)) {
                            startActivity(new Intent(mContext, SettingsActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Invalid Password!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                mAlertDialog.show();

            }
        });

        mItem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mSideMenuView.setVisibility(View.GONE);
                mSideMenuView.startAnimation(mAnimationOut);
                mIsVisible = false;

                startActivity(new Intent(mContext, UpdatePasswordActivity.class));

            }
        });

        mItem3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mSideMenuView.setVisibility(View.GONE);
                mSideMenuView.startAnimation(mAnimationOut);
                mIsVisible = false;

                final EditText mPasscode;
                LinearLayout.LayoutParams mLayoutParams = null;

                mPasscode = new EditText(mContext);
                mPasscode.setHint(R.string.passcode);
                mPasscode.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD);
                mPasscode.setTransformationMethod(PasswordTransformationMethod.getInstance());

                mLayoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                mPasscode.setLayoutParams(mLayoutParams);

                builder = new AlertDialog.Builder(mContext, R.style.AppCompatAlertDialogStyle);
                builder.setTitle("Pin Verification");
                builder.setView(mPasscode, 50, 50, 50, 50);
                builder.setIcon(R.drawable.icon_lock);
                builder.setCancelable(false);

                mAlertDialog = builder.create();

                mAlertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        mStrPasscode = mPasscode.getText().toString();

                        String mTemp = "sri@" + new AppPreferences().getName();

                        if (mStrPasscode != null && mStrPasscode.equals(mTemp)) {
                            startActivity(new Intent(mContext, DailyReportActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Invalid Password!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                mAlertDialog.show();
            }
        });

        mItem4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mSideMenuView.setVisibility(View.GONE);
                mSideMenuView.startAnimation(mAnimationOut);
                mIsVisible = false;

                final EditText mPasscode;
                LinearLayout.LayoutParams mLayoutParams = null;

                mPasscode = new EditText(mContext);
                mPasscode.setHint(R.string.passcode);
                mPasscode.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_PASSWORD);
                mPasscode.setTransformationMethod(PasswordTransformationMethod.getInstance());

                mLayoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                mPasscode.setLayoutParams(mLayoutParams);

                builder = new AlertDialog.Builder(mContext, R.style.AppCompatAlertDialogStyle);
                builder.setTitle("Count Reset");
                builder.setView(mPasscode, 50, 50, 50, 50);
                builder.setCancelable(false);

                mAlertDialog = builder.create();

                mAlertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        mStrPasscode = mPasscode.getText().toString();

                        String mTemp = "sri@" + new AppPreferences().getName();

                        if (mStrPasscode != null && mStrPasscode.equals(mTemp)) {

                            mPreferences.setPrintCount(0);
                            mPreferences.setPrintParcelCount(0);

                            mPreferences.setLSerialNo(0);
                            mPreferences.setPSerialNo(0);

                            Toast.makeText(mContext, "Count reset done", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Invalid Password!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                mAlertDialog.show();
            }
        });

        mItem5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mTodayDate = mSimpleDateFormat.format(calendar.getTime());

                LayoutInflater mInflater = getLayoutInflater();
                View mView = mInflater.inflate(R.layout.return_count_view, null);

                mSideMenuView.setVisibility(View.GONE);
                mSideMenuView.startAnimation(mAnimationOut);
                mIsVisible = false;

                builder = new AlertDialog.Builder(mContext, R.style.AppCompatAlertDialogStyle);
                builder.setTitle("Return Token");
                builder.setIcon(R.drawable.ic_returns);
                builder.setView(mView);
                builder.setCancelable(false);

                mAlertDialog = builder.create();

                mTokenId = (EditText) mView.findViewById(R.id.token_id);
                mReturnCount = (EditText) mView.findViewById(R.id.edit_count);

                mAlertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        mStrCount = mReturnCount.getText().toString();
                        mTokId = mTokenId.getText().toString();

                        if (mStrCount.length() == 0 || Integer.parseInt(mStrCount) <= 0) {
                            Toast.makeText(mContext, "Invalid Count", Toast.LENGTH_SHORT).show();
                        } else if (mStrCount.length() == 0) {
                            Toast.makeText(mContext, "Invalid Token Id", Toast.LENGTH_SHORT).show();
                        } else {
                            int xx = new HotelDatabase(mContext).UpdateReturnToken(mTokId, Integer.parseInt(mStrCount));

                            if (xx == 1) {
                                Toast.makeText(mContext, "Token Updated", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(mContext, "Invalid Token Id", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

                mAlertDialog.show();
            }
        });

        mItem6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mItem6.setEnabled(false);

                mSideMenuView.setVisibility(View.GONE);
                mSideMenuView.startAnimation(mAnimationOut);
                mIsVisible = false;

                ScreenLunch.getListener().Reprint();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_settings:

                if (!mIsVisible) {
                    mSideMenuView.setVisibility(View.VISIBLE);
                    mSideMenuView.startAnimation(mAnimationIn);
                    mIsVisible = true;
                } else {
                    mSideMenuView.setVisibility(View.GONE);
                    mSideMenuView.startAnimation(mAnimationOut);
                    mIsVisible = false;
                }

                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}