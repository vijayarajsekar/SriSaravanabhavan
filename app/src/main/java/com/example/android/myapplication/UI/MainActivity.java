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
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.example.android.myapplication.Adapter.PagerAdapter;
import com.example.android.myapplication.R;

public class MainActivity extends FragmentActivity {

    private String TAG = MainActivity.class.getSimpleName();

    private PagerSlidingTabStrip tabs;
    private ViewPager pager;
    private PagerAdapter mPagerAdapter;

    private Context mContext;

    private LinearLayout mSideMenuView;
    private TextView mItem1, mItem2, mItem3;

    private boolean mIsVisible = false;

    private Animation mAnimationIn, mAnimationOut;

    private String mStrPasscode;

    private AlertDialog.Builder builder;
    private AlertDialog mAlertDialog;

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

        mAnimationIn = AnimationUtils.loadAnimation(mContext, R.anim.go_right_in);
        mAnimationOut = AnimationUtils.loadAnimation(mContext, R.anim.go_right_out);

        mItem1 = (TextView) findViewById(R.id.menuview_item1);
        mItem2 = (TextView) findViewById(R.id.menuview_item2);
        mItem3 = (TextView) findViewById(R.id.menuview_item3);

        pager.setAdapter(mPagerAdapter);

        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);

        tabs.setViewPager(pager);

        mItem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mItem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

                        if (mStrPasscode != null && mStrPasscode.toString().length() > 5 && mStrPasscode.toString().equals("qwerty")) {
                            startActivity(new Intent(mContext, DailyReportActivity.class));
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Invalid Password!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
//                mAlertDialog.show();

                startActivity(new Intent(mContext, DailyReportActivity.class));

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