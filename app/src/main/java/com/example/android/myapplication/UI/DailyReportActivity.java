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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.screen_dailyreport);

        mContext = this;

        mHotelDatabase = new HotelDatabase(mContext);

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

//        for (int x = 1; x < 10; x++) {
//
////            mDataList.add(new PrintPojo("" + x, "07/" + x + "/1986", "" + x * 10));
//
//            mHotelDatabase.InsertRecord("User " + x, "2016-" + "0" + x + "-01", String.valueOf(x * 10));
//        }

        mHotelDatabase.InsertRecord("User " + 1, "2016-" + "0" + 1 + "-01", String.valueOf(99 * 10));
        mHotelDatabase.InsertRecord("User " + 2, "2016-" + "0" + 2 + "-01", String.valueOf(9 * 10));
        mHotelDatabase.InsertRecord("User " + 3, "2016-" + "0" + 2 + "-01", String.valueOf(8 * 10));
        mHotelDatabase.InsertRecord("User " + 4, "2016-" + "0" + 2 + "-01", String.valueOf(7 * 10));
        mHotelDatabase.InsertRecord("User " + 5, "2016-" + "0" + 5 + "-01", String.valueOf(5 * 10));
        mHotelDatabase.InsertRecord("User " + 6, "2016-" + "0" + 6 + "-01", String.valueOf(1 * 10));
        mHotelDatabase.InsertRecord("User " + 7, "2016-" + "0" + 7 + "-01", String.valueOf(2 * 10));
        mHotelDatabase.InsertRecord("User " + 8, "2016-" + "0" + 8 + "-01", String.valueOf(2 * 10));
        mHotelDatabase.InsertRecord("User " + 9, "2016-" + "0" + 9 + "-01", String.valueOf(3 * 10));
        mHotelDatabase.InsertRecord("User " + 20, "2016-" + "0" + 9 + "-01", String.valueOf(3 * 10));

//
//        mDataList = mHotelDatabase.GetAllCounts();
//
//        if (mDataList.size() != 0) {
//
//            mTotalCountAdapter = new TotalCountAdapter(mContext, mDataList);
//
//            mListView.setAdapter(mTotalCountAdapter);
//        }

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

                        mDataList = mHotelDatabase.GetAllCountDateWise(mFromDate.getText().toString(), mToDate.getText().toString());

                        if (mDataList.size() != 0) {
                            mTotalCountAdapter = new TotalCountAdapter(mContext, mDataList);
                            mListView.setAdapter(mTotalCountAdapter);
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
