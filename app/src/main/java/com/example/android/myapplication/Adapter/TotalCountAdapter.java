package com.example.android.myapplication.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.myapplication.Models.PrintPojo;
import com.example.android.myapplication.R;

import java.util.Calendar;
import java.util.List;

/**
 * Created by vijayarajsekar on 12/11/15.
 */

public class TotalCountAdapter extends BaseAdapter {

    private Context mContext;
    private long mMillisNow;
    private int mToday;

    private ViewHolder holder;
    private LayoutInflater l_Inflater;
    private List<PrintPojo> mReportList = null;


    public TotalCountAdapter(Context mContext, List<PrintPojo> mDataList) {
        this.mContext = mContext;
        l_Inflater = LayoutInflater.from(mContext);
        this.mReportList = mDataList;
    }

    @Override
    public int getCount() {
        return this.mReportList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        if (convertView == null) {

            Calendar calendarNow = Calendar.getInstance();
            mMillisNow = calendarNow.getTimeInMillis();
            mToday = calendarNow.get(Calendar.DAY_OF_MONTH);

            holder = new ViewHolder();

            convertView = l_Inflater.inflate(R.layout.total_count_adapter, parent, false);

            holder = new ViewHolder();

            holder.mSno = (TextView) convertView.findViewById(R.id.txt_sno);
            holder.mDate = (TextView) convertView.findViewById(R.id.txt_date);
            holder.mCount = (TextView) convertView.findViewById(R.id.txt_count);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mSno.setText(mReportList.get(position).getSno());
        holder.mDate.setText(mReportList.get(position).getDateWise());
        holder.mCount.setText(mReportList.get(position).getTotalCount());

        return convertView;
    }

    private class ViewHolder {

        private TextView mSno, mDate, mCount;

    }
}
