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

        mHotelDatabase.InsertRecord("User " + 1, "2016-" + "0" + 1 + "-01", String.valueOf(10), "L", "A1");
        mHotelDatabase.InsertRecord("User " + 2, "2016-" + "0" + 1 + "-01", String.valueOf(100), "L", "A2");
        mHotelDatabase.InsertRecord("User " + 3, "2016-" + "0" + 1 + "-01", String.valueOf(8), "L", "A3");
        mHotelDatabase.InsertRecord("User " + 4, "2016-" + "0" + 2 + "-01", String.valueOf(7 * 10), "P", "A4");
        mHotelDatabase.InsertRecord("User " + 5, "2016-" + "0" + 5 + "-01", String.valueOf(5 * 10), "P", "A5");
        mHotelDatabase.InsertRecord("User " + 6, "2016-" + "0" + 6 + "-06", String.valueOf(5 * 10), "L", "A6");
        mHotelDatabase.InsertRecord("User " + 7, "2016-" + "0" + 7 + "-01", String.valueOf(2 * 10), "P", "A7");
        mHotelDatabase.InsertRecord("User " + 8, "2016-" + "0" + 8 + "-01", String.valueOf(2 * 10), "L", "A8");
        mHotelDatabase.InsertRecord("User " + 9, "2016-" + "0" + 9 + "-01", String.valueOf(3 * 10), "L", "A9");
        mHotelDatabase.InsertRecord("User " + 20, "2016-" + "0" + 9 + "-01", String.valueOf(3 * 10), "P", "A0");

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
