package com.example.android.myapplication.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.datetimepicker.date.DatePickerDialog;
import com.example.android.myapplication.Adapter.TotalCountAdapter;
import com.example.android.myapplication.Database.HotelDatabase;
import com.example.android.myapplication.Models.PrintPojo;
import com.example.android.myapplication.Preferences.AppPreferences;
import com.example.android.myapplication.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DailyReportActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private String TAG = DailyReportActivity.class.getSimpleName();

    private EditText mFromDate, mToDate;
    private Button mSearch;
    private ListView mListView;

    private List<PrintPojo> mDataList;

    private TotalCountAdapter mTotalCountAdapter;

    private Context mContext;

    private Calendar calendar;

    private SimpleDateFormat mSimpleDateFormat;

    private boolean mIsfirst = true;

    private Date mCompareDate1, mCompareDate2;

    private HotelDatabase mHotelDatabase;

    private RadioGroup mRadioGroup;
    private RadioButton mRadioType;

    private String mFoodType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.screen_dailyreport);

        mContext = this;

        mHotelDatabase = new HotelDatabase(mContext);

        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        mFromDate = (EditText) findViewById(R.id.date_from);
        mToDate = (EditText) findViewById(R.id.date_to);

        mListView = (ListView) findViewById(R.id.datalist);

        mSearch = (Button) findViewById(R.id.btn_search);

//        mDataList = new ArrayList<PrintPojo>();

        calendar = Calendar.getInstance();
        mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {

            mFromDate.setText(mSimpleDateFormat.format(calendar.getTime()));
            mCompareDate1 = mSimpleDateFormat.parse(mSimpleDateFormat.format(calendar.getTime()));

            mToDate.setText(mSimpleDateFormat.format(calendar.getTime()));
            mCompareDate2 = mSimpleDateFormat.parse(mSimpleDateFormat.format(calendar.getTime()));

        } catch (Exception ex) {
            Log.v(TAG, ex.toString());
        }



        if (!new AppPreferences().getDummyInsert()) {

            mHotelDatabase.InsertRecord("VCS ", "2016-11-10", "249", "L", "A1");
            mHotelDatabase.InsertRecord("VCS ", "2016-11-10", "28", "P", "A2");
            mHotelDatabase.InsertRecord("VCS ", "2016-11-11", "326", "L", "A3");
            mHotelDatabase.InsertRecord("VCS ", "2016-11-11", "52", "P", "A4");
//
//            mHotelDatabase.InsertRecord("VCS ", "2016-11-15", "1", "L", "A1");
//            mHotelDatabase.InsertRecord("VCS ", "2016-11-15", "2", "L", "A2");
//            mHotelDatabase.InsertRecord("VCS ", "2016-11-15", "4", "L", "A3");
//            mHotelDatabase.InsertRecord("VCS ", "2016-11-15", "7", "L", "A4");
//            mHotelDatabase.InsertRecord("VCS ", "2016-11-15", "2", "L", "A5");
//            mHotelDatabase.InsertRecord("VCS ", "2016-11-15", "1", "L", "A6");
//
//            mHotelDatabase.InsertRecord("VCS ", "2016-11-15", "67", "L", "A7");
//            mHotelDatabase.InsertRecord("VCS ", "2016-11-15", "25", "L", "A8");
//
//            mHotelDatabase.InsertRecord("VCS ", "2016-11-15", "14", "L", "A9");
//            mHotelDatabase.InsertRecord("VCS ", "2016-11-15", "5", "L", "A10");
//            mHotelDatabase.InsertRecord("VCS ", "2016-11-15", "6", "L", "A11");
//            mHotelDatabase.InsertRecord("VCS ", "2016-11-15", "47", "L", "A12");
//            mHotelDatabase.InsertRecord("VCS ", "2016-11-15", "3", "L", "A13");
//            mHotelDatabase.InsertRecord("VCS ", "2016-11-15", "9", "L", "A14");
//
//            mHotelDatabase.InsertRecord("VCS ", "2016-11-15", "27", "L", "A15");
//
//            mHotelDatabase.InsertRecord("VCS ", "2016-11-15", "1", "L", "A16");
//            mHotelDatabase.InsertRecord("VCS ", "2016-11-15", "4", "L", "A17");
//            mHotelDatabase.InsertRecord("VCS ", "2016-11-15", "2", "L", "A18");
//            mHotelDatabase.InsertRecord("VCS ", "2016-11-15", "5", "L", "A19");
//            mHotelDatabase.InsertRecord("VCS ", "2016-11-15", "3", "L", "A20");
//            mHotelDatabase.InsertRecord("VCS ", "2016-11-15", "7", "L", "A21");
//            mHotelDatabase.InsertRecord("VCS ", "2016-11-15", "1", "L", "A22");
//            mHotelDatabase.InsertRecord("VCS ", "2016-11-15", "2", "L", "A23");
//            mHotelDatabase.InsertRecord("VCS ", "2016-11-15", "4", "L", "A24");
//            mHotelDatabase.InsertRecord("VCS ", "2016-11-15", "6", "L", "A25");
//
//            mHotelDatabase.InsertRecord("VCS ", "2016-11-15", "7", "L", "A26");
//            mHotelDatabase.InsertRecord("VCS ", "2016-11-15", "2", "L", "A27");
//            mHotelDatabase.InsertRecord("VCS ", "2016-11-15", "4", "L", "A28");
//            mHotelDatabase.InsertRecord("VCS ", "2016-11-15", "4", "L", "A29");
//
//            mHotelDatabase.InsertRecord("VCS ", "2016-11-15", "7", "L", "A30");
//
//            mHotelDatabase.InsertRecord("VCS ", "2016-11-15", "1", "L", "A31");
//
//            mHotelDatabase.InsertRecord("VCS ", "2016-11-15", "4", "L", "A32");
//
//            mHotelDatabase.InsertRecord("VCS ", "2016-11-15", "5", "L", "A33");
//            mHotelDatabase.InsertRecord("VCS ", "2016-11-15", "2", "L", "A34");
//            mHotelDatabase.InsertRecord("VCS ", "2016-11-15", "2", "L", "A35");
//
//            // ------------------------------- //
//
//            mHotelDatabase.InsertRecord("VCS ", "2016-11-15", "5", "P", "Q1");
//            mHotelDatabase.InsertRecord("VCS ", "2016-11-15", "1", "P", "Q2");
//            mHotelDatabase.InsertRecord("VCS ", "2016-11-15", "54", "P", "Q3");
//
//            mHotelDatabase.InsertRecord("VCS ", "2016-11-15", "67", "P", "Q4");
//            mHotelDatabase.InsertRecord("VCS ", "2016-11-15", "45", "P", "Q5");
//            mHotelDatabase.InsertRecord("VCS ", "2016-11-15", "9", "P", "Q6");
//
//            mHotelDatabase.InsertRecord("VCS ", "2016-11-15", "14", "P", "Q7");
//            mHotelDatabase.InsertRecord("VCS ", "2016-11-15", "3", "P", "Q8");
//            mHotelDatabase.InsertRecord("VCS ", "2016-11-15", "6", "P", "Q9");
//
//            mHotelDatabase.InsertRecord("VCS ", "2016-11-15", "6", "P", "Q10");
//            mHotelDatabase.InsertRecord("VCS ", "2016-11-15", "1", "P", "Q11");


            new AppPreferences().setDummyInsert(true);
        }

        /**
         *      VCS - 2016-11-15 - 249 - L - A1
         *      VCS - 2016-11-15 - 28 - P - A2
         *
         *
         *      VCS - 2016-11-15 - 326 - L - A3
         *      VCS - 2016-11-15 - 52 - P - A4
         *
         */

        mFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIsfirst = true;
                DatePickerDialog.newInstance(DailyReportActivity.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show(getFragmentManager(), "datePicker");
            }
        });

        mToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIsfirst = false;
                DatePickerDialog.newInstance(DailyReportActivity.this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show(getFragmentManager(), "datePicker");
            }
        });

        mSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (mFromDate != null && mFromDate.getText().toString().trim().length() == 0) {
                    Toast.makeText(mContext, "Invalid From Date", Toast.LENGTH_SHORT).show();
                } else if (mToDate != null && mToDate.getText().toString().trim().length() == 0) {
                    Toast.makeText(mContext, "Invalid To Date", Toast.LENGTH_SHORT).show();
                } else {
                    if (mCompareDate1.after(new Date())) {
                        Toast.makeText(mContext, "From Date is Greater Than Today", Toast.LENGTH_SHORT).show();
                    } else if (mCompareDate2.after(new Date())) {
                        Toast.makeText(mContext, "To Date is Greater Than Today", Toast.LENGTH_SHORT).show();
                    } else {

                        // get selected radio button from radioGroup
                        int selectedId = mRadioGroup.getCheckedRadioButtonId();

                        // find the radiobutton by returned id
                        mRadioType = (RadioButton) findViewById(selectedId);

                        if (mRadioType.getText().toString().equals("Lunch")) {
                            mFoodType = "L";
                        } else {
                            mFoodType = "P";
                        }

                        mDataList = mHotelDatabase.GetAllCountDateWise(mFromDate.getText().toString(), mToDate.getText().toString(), mFoodType);

                        if (mDataList.size() != 0) {
                            mTotalCountAdapter = new TotalCountAdapter(mContext, mDataList);
                            mListView.setAdapter(mTotalCountAdapter);
                        } else {
                            mListView.setAdapter(null);
                            Toast.makeText(mContext, "No data available", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {

                int mpos = pos + 1;

                Intent mIntent = new Intent(mContext, PrintDailyCount.class);
                mIntent.putExtra("SNO", "" + mpos);
                mIntent.putExtra("QTY", mDataList.get(pos).getTotalCount());
                mIntent.putExtra("TYPE", mFoodType);
                mIntent.putExtra("DATE", mDataList.get(pos).getDateWise());
                mIntent.putExtra("RETCOUNT", mDataList.get(pos).getRetCount());
                startActivity(mIntent);
//                mDataList

            }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(year, monthOfYear, dayOfMonth);
        update();
    }

    private void update() {

        try {

            if (mIsfirst) {
                mFromDate.setText(mSimpleDateFormat.format(calendar.getTime()));
                mCompareDate1 = mSimpleDateFormat.parse(mSimpleDateFormat.format(calendar.getTime()));
            } else {
                mToDate.setText(mSimpleDateFormat.format(calendar.getTime()));
                mCompareDate2 = mSimpleDateFormat.parse(mSimpleDateFormat.format(calendar.getTime()));
            }
        } catch (Exception ex) {
            Log.v(TAG, ex.toString());
        }
    }
}
