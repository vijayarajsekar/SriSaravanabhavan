package com.example.android.myapplication.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.printer.Printer;
import com.epson.epos2.printer.PrinterStatusInfo;
import com.epson.epos2.printer.ReceiveListener;
import com.example.android.myapplication.Database.HotelDatabase;
import com.example.android.myapplication.Preferences.AppPreferences;
import com.example.android.myapplication.R;
import com.example.android.myapplication.UI.SettingsActivity;
import com.example.android.myapplication.Utils.ShowMsg;
import com.example.android.myapplication.Utils.WiFiBTStatus;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

/**
 * Created by VijayarajSekar on 8/31/2016.
 */
public class ScreenLunch extends Fragment implements ReceiveListener, View.OnClickListener {

    private View mRootView;
    private Context mContext;

    private Button mClear;

    private EditText mEditCount;

    private WiFiBTStatus mWiFiBTStatus;

    private Printer mPrinter = null;

    private AppPreferences mPreferences;

    private ProgressDialog mProgressDialog;

    private int mPrintCount = 0;

    public static boolean mIsParcel = false;

    private HotelDatabase mHotelDatabase;

    private Calendar mCalendar;

    private SimpleDateFormat mSimpleDateFormat, mSimpleDateFormat1;

    private String mTimeStamp, mTimeStamp1;

    private String[] mTimeStampTemp;

    private java.util.Date mDate;

    private String Date1, mTime;

    private String mTotalCount = "0";

    private int mTotalCountTop = 0;
    private int mTotalCountTopParcel = 0;
    private String uniqueId;
    private TextView mText1, mText2, mText3, mText4, mText5, mText6, mText7, mText8, mText9, mText0, mTextFm, mTextPm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.screen_lunch, container, false);
        mContext = getActivity();

//        initializeObject();

        mPreferences = new AppPreferences();

        mWiFiBTStatus = new WiFiBTStatus();

        mHotelDatabase = new HotelDatabase(mContext);

        mDate = new java.util.Date();

        mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        mSimpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd hh:mm a");

        mCalendar = Calendar.getInstance();

        mEditCount = (EditText) mRootView.findViewById(R.id.edit_count);

//        mTextFm = (TextView) mRootView.findViewById(R.id.btn_token);
//        mTextPm = (TextView) mRootView.findViewById(R.id.btn_parcel);

        mClear = (Button) mRootView.findViewById(R.id.btn_clear);
        mClear.setOnClickListener(this);

        mText1 = (TextView) mRootView.findViewById(R.id.txt_1);
        mText1.setOnClickListener(this);

        mText2 = (TextView) mRootView.findViewById(R.id.txt_2);
        mText2.setOnClickListener(this);

        mText3 = (TextView) mRootView.findViewById(R.id.txt_3);
        mText3.setOnClickListener(this);

        mText4 = (TextView) mRootView.findViewById(R.id.txt_4);
        mText4.setOnClickListener(this);

        mText5 = (TextView) mRootView.findViewById(R.id.txt_5);
        mText5.setOnClickListener(this);

        mText6 = (TextView) mRootView.findViewById(R.id.txt_6);
        mText6.setOnClickListener(this);

        mText7 = (TextView) mRootView.findViewById(R.id.txt_7);
        mText7.setOnClickListener(this);

        mText8 = (TextView) mRootView.findViewById(R.id.txt_8);
        mText8.setOnClickListener(this);

        mText9 = (TextView) mRootView.findViewById(R.id.txt_9);
        mText9.setOnClickListener(this);

        mText0 = (TextView) mRootView.findViewById(R.id.txt_0);
        mText0.setOnClickListener(this);

        mTextFm = (TextView) mRootView.findViewById(R.id.txt_fm);
        mTextPm = (TextView) mRootView.findViewById(R.id.txt_pm);

        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Printing");

        mTextFm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                System.out.println("DATE - - - > " + Date1 + "TIME - - - > " + mTime);

                mCalendar = Calendar.getInstance();
                mTimeStamp1 = mSimpleDateFormat1.format(mCalendar.getTime());

                mTimeStampTemp = mTimeStamp1.split(" ");
                Date1 = mTimeStampTemp[0];
                mTime = mTimeStampTemp[1];

                uniqueId = GetUUID();

                System.out.println(" - - DATE - - " + Date1 + " - - TIME - - " + mTime);

//                mPreferences.setPrintCount(mPreferences.getPrintCount() + 1);
//                mPreferences.setPrintParcelCount(mPreferences.getPrintParcelCount() + 1);
//
//                System.out.println(" - - TOK - - " + mPreferences.getPrintCount() + " - - PAR - - " + mPreferences.getPrintParcelCount());

                if (mWiFiBTStatus.isBluetoothAvailable()) {

                    if (new AppPreferences().getPrinterModel() != -1) {

//                        mCalendar = Calendar.getInstance();
//                        mTimeStamp1 = mSimpleDateFormat1.format(mCalendar.getTime());
//
//                        mTimeStampTemp = mTimeStamp1.split(" ");
//                        Date1 = mTimeStampTemp[0];
//                        mTime = mTimeStampTemp[1];

                        if (Integer.parseInt(mTotalCount) > 0 && Integer.parseInt(mTotalCount) < 101) {
                            Toast.makeText(mContext, "Printing Started . . .", Toast.LENGTH_SHORT).show();
                            mIsParcel = false;
                            mTextFm.setEnabled(false);
                            mTextFm.setBackgroundColor(Color.parseColor("#ACAAAB"));

                            mProgressDialog.show();

                            System.out.println(" - - YES 0 - - " + String.valueOf(mPreferences.getPrintCount() - 2) + " - - NO - - " + mPreferences.getPrintCount());

                            mPreferences.setPrintCount(mPreferences.getPrintCount() + Integer.parseInt(mTotalCount));

                            mTotalCountTop = mPreferences.getPrintCount()/* + Integer.parseInt(mTotalCount)*/;

                            mPrintCount = 1;

                            runPrintReceiptSequence();

                        } else {
                            Toast.makeText(mContext, "Invalid count", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        ShowMsg.showMsg("Printer setting is not available.", mContext);

                        mTextFm.setEnabled(true);
                        mTextFm.setBackgroundColor(Color.parseColor("#F95630"));
                        mProgressDialog.dismiss();

//                        mPreferences.setPrintCount(mPreferences.getPrintCount() - 1);
                    }

                } else {
                    ShowMsg.showMsg("Enabling Bluetooth", mContext);

                    mTextFm.setEnabled(true);
                    mTextFm.setBackgroundColor(Color.parseColor("#F95630"));
                    mProgressDialog.dismiss();
//                    mPreferences.setPrintCount(mPreferences.getPrintCount() - 1);

                    mWiFiBTStatus.SetBluetoothStatus(true);
                }
            }
        });

        mTextPm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mCalendar = Calendar.getInstance();
                mTimeStamp1 = mSimpleDateFormat1.format(mCalendar.getTime());

                uniqueId = GetUUID();

                mTimeStampTemp = mTimeStamp1.split(" ");
                Date1 = mTimeStampTemp[0];
                mTime = mTimeStampTemp[1];
//                Toast.makeText(mContext, mTotalCount, Toast.LENGTH_SHORT).show();
                System.out.println(" - - DATE - - " + Date1 + " - - TIME - - " + mTime);

                if (mWiFiBTStatus.isBluetoothAvailable()) {

                    if (new AppPreferences().getPrinterModel() != -1) {

                        if (Integer.parseInt(mTotalCount) > 0 && Integer.parseInt(mTotalCount) < 101) {
//                        mCalendar = Calendar.getInstance();
//
//                        mTimeStamp1 = mSimpleDateFormat1.format(mCalendar.getTime());
//
//                        mTimeStampTemp = mTimeStamp1.split(" ");
//                        Date1 = mTimeStampTemp[0];
//                        mTime = mTimeStampTemp[1];
//
//                        System.out.println(" - - DATE - - " + Date1 + " - - TIME - - " + mTime);

                            mIsParcel = true;
                            mTextPm.setEnabled(false);
                            mTextFm.setBackgroundColor(Color.parseColor("#ACAAAB"));
                            mProgressDialog.show();
                            System.out.println(" - - YES 0 - - " + String.valueOf(mPreferences.getPrintParcelCount() - 2) + " - - NO - - " + mPreferences.getPrintParcelCount());

                            mPreferences.setPrintParcelCount(mPreferences.getPrintParcelCount() + Integer.parseInt(mTotalCount));

                            mTotalCountTopParcel = mPreferences.getPrintParcelCount() /*+ Integer.parseInt(mTotalCount)*/;

                            mPrintCount = 1;

                            runPrintReceiptSequence();
                        } else {
                            Toast.makeText(mContext, "Invalid count", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        ShowMsg.showMsg("Printer setting is not available.", mContext);

                        mTextPm.setEnabled(true);
                        mTextPm.setBackgroundColor(Color.parseColor("#00A083"));
                        mProgressDialog.dismiss();
//                        mPreferences.setPrintCount(mPreferences.getPrintCount() - 1);
                    }

                } else {
                    ShowMsg.showMsg("Enabling Bluetooth", mContext);

                    mTextPm.setEnabled(true);
                    mTextPm.setBackgroundColor(Color.parseColor("#00A083"));
                    mProgressDialog.dismiss();
//                    mPreferences.setPrintCount(mPreferences.getPrintCount() - 1);

                    mWiFiBTStatus.SetBluetoothStatus(true);
                }
            }
        });

        return mRootView;
    }

    public boolean runPrintReceiptSequence() {

        if (mPrintCount > 0 && mPrintCount < 3) {

            if (!initializeObject()) {
                return false;
            }

            if (!createReceiptData()) {
                finalizeObject();
                return false;
            }

            if (!printData()) {
                finalizeObject();
                return false;
            }
        }

        return true;
    }

    private boolean createReceiptData() {

        String method = "";
        Bitmap logoData = BitmapFactory.decodeResource(getResources(), R.drawable.storre);
        StringBuilder textData = new StringBuilder();
        final int barcodeWidth = 2;
        final int barcodeHeight = 100;

        if (mPrinter == null) {
            return false;
        }

        try {

            if (mPreferences.getPrintType() == 1) {

                method = "addTextAlign";
                mPrinter.addTextAlign(Printer.ALIGN_CENTER);

                method = "addTextSize";
                mPrinter.addTextSize(1, 1);

                method = "addText";

                if (mIsParcel) {
                    mPrinter.addText(uniqueId + "                            " + mTotalCountTopParcel + " \n");
                } else {
                    mPrinter.addText(uniqueId + "                            " + mTotalCountTop + " \n");
                }

                method = "addImage";
                mPrinter.addImage(logoData, 0, 0,
                        logoData.getWidth(),
                        logoData.getHeight(),
                        Printer.COLOR_1,
                        Printer.MODE_MONO,
                        Printer.HALFTONE_DITHER,
                        Printer.PARAM_DEFAULT,
                        Printer.COMPRESS_NONE);

                method = "addFeedLine";
                mPrinter.addFeedLine(1);

                method = "addTextSize";
                mPrinter.addTextSize(2, 2);
                method = "addText";
                mPrinter.addText("SHREE SARAVANABHAVAN \n CLASSIC \n");

                method = "addFeedLine";
                mPrinter.addFeedLine(1);

                method = "addTextSize";
                mPrinter.addTextSize(1, 1);

                method = "addText";
                mPrinter.addText("(High Class Veg Restaurant) \n");

                method = "addTextSize";
                mPrinter.addTextSize(1, 1);

                method = "addText";
                mPrinter.addText("Hotel City View, Opp New Bus Stand, \n");

                method = "addTextSize";
                mPrinter.addTextSize(1, 1);

                method = "addText";
                mPrinter.addText("Salem - 4 \n");

                method = "addTextSize";
                mPrinter.addTextSize(1, 1);

                method = "addText";
                mPrinter.addText("Ph: 0427-2433702 \n");

                method = "addTextSize";
                mPrinter.addTextSize(1, 1);

                method = "addText";
                mPrinter.addText("Date :" + Date1 + " Time : " + mTime + "\n");

                method = "addTextSize";
                mPrinter.addTextSize(1, 1);
                textData.append(".........................................\n");

                method = "addText";
                mPrinter.addText(textData.toString());
                textData.delete(0, textData.length());

                method = "addFeedLine";
                mPrinter.addFeedLine(1);

                method = "addTextSize";
                mPrinter.addTextSize(1, 1);

                method = "addText";

                if (mIsParcel) {
                    mPrinter.addText("Parcel Meals       - ");
                } else {
                    mPrinter.addText("Full Meals       - ");
                }

                method = "addTextSize";
                mPrinter.addTextSize(2, 2);

                method = "addText";

                if (mIsParcel) {
                    mPrinter.addText(" KOT " + mTotalCount + " \n");
                } else {
                    mPrinter.addText(" KOT " + mTotalCount + " \n");
                }

                method = "addFeedLine";
                mPrinter.addFeedLine(1);

                method = "addTextSize";
                mPrinter.addTextSize(1, 1);

                textData.append(".........................................\n");
                method = "addText";

                mPrinter.addText(textData.toString());
                textData.delete(0, textData.length());

                method = "addFeedLine";
                mPrinter.addFeedLine(4);

                // - - - - - - 2nd Print - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

                // Setting The Count Sequence
                //  mPreferences.setPrintCount(mPreferences.getPrintCount() + 1);

                method = "addTextAlign";
                mPrinter.addTextAlign(Printer.ALIGN_CENTER);

                method = "addTextSize";
                mPrinter.addTextSize(1, 1);

                method = "addText";
                if (mIsParcel) {
                    mPrinter.addText(uniqueId + "                            " + mTotalCountTopParcel + " \n");
                } else {
                    mPrinter.addText(uniqueId + "                            " + mTotalCountTop + " \n");
                }

                method = "addImage";
                mPrinter.addImage(logoData, 0, 0,
                        logoData.getWidth(),
                        logoData.getHeight(),
                        Printer.COLOR_1,
                        Printer.MODE_MONO,
                        Printer.HALFTONE_DITHER,
                        Printer.PARAM_DEFAULT,
                        Printer.COMPRESS_NONE);

                method = "addFeedLine";
                mPrinter.addFeedLine(1);

                method = "addTextSize";
                mPrinter.addTextSize(2, 2);
                method = "addText";
                mPrinter.addText("SHREE SARAVANABHAVAN \n CLASSIC \n");

                method = "addFeedLine";
                mPrinter.addFeedLine(1);

                method = "addTextSize";
                mPrinter.addTextSize(1, 1);

                method = "addText";
                mPrinter.addText("(High Class Veg Restaurant) \n");

                method = "addTextSize";
                mPrinter.addTextSize(1, 1);

                method = "addText";
                mPrinter.addText("Hotel City View, Opp New Bus Stand, \n");

                method = "addTextSize";
                mPrinter.addTextSize(1, 1);

                method = "addText";
                mPrinter.addText("Salem - 4 \n");

                method = "addTextSize";
                mPrinter.addTextSize(1, 1);

                method = "addText";
                mPrinter.addText("Ph: 0427-2433702 \n");

                method = "addTextSize";
                mPrinter.addTextSize(1, 1);

                method = "addText";
                mPrinter.addText("Date :" + Date1 + " Time : " + mTime + "\n");

                method = "addTextSize";
                mPrinter.addTextSize(1, 1);
                textData.append(".........................................\n");

                method = "addText";
                mPrinter.addText(textData.toString());
                textData.delete(0, textData.length());

                method = "addFeedLine";
                mPrinter.addFeedLine(1);

                method = "addTextSize";
                mPrinter.addTextSize(1, 1);

                if (mIsParcel) {
                    mPrinter.addText("Parcel Meals      - ");
                } else {
                    mPrinter.addText("Full Meals       - ");
                }

                method = "addTextSize";
                mPrinter.addTextSize(2, 2);

                method = "addText";

                if (mIsParcel) {
                    mPrinter.addText(" KOT " + mTotalCount + " \n");
                } else {
                    mPrinter.addText(" KOT " + mTotalCount + " \n");
                }

                method = "addTextSize";
                mPrinter.addTextSize(1, 1);

                textData.append(".........................................\n");
                method = "addText";

                mPrinter.addText(textData.toString());
                textData.delete(0, textData.length());

                method = "addFeedLine";
                mPrinter.addFeedLine(4);

            } else {

                method = "addTextAlign";
                mPrinter.addTextAlign(Printer.ALIGN_CENTER);

                method = "addTextSize";
                mPrinter.addTextSize(1, 1);

                method = "addText";
                if (mIsParcel) {
                    mPrinter.addText(uniqueId + "                            " + mTotalCountTopParcel + " \n");
                } else {
                    mPrinter.addText(uniqueId + "                            " + mTotalCountTop + " \n");
                }

                method = "addImage";
                mPrinter.addImage(logoData, 0, 0,
                        logoData.getWidth(),
                        logoData.getHeight(),
                        Printer.COLOR_1,
                        Printer.MODE_MONO,
                        Printer.HALFTONE_DITHER,
                        Printer.PARAM_DEFAULT,
                        Printer.COMPRESS_NONE);

                method = "addFeedLine";
                mPrinter.addFeedLine(1);

                method = "addTextSize";
                mPrinter.addTextSize(2, 2);
                method = "addText";
                mPrinter.addText("SHREE SARAVANABHAVAN \n CLASSIC \n");

                method = "addFeedLine";
                mPrinter.addFeedLine(1);

                method = "addTextSize";
                mPrinter.addTextSize(1, 1);

                method = "addText";
                mPrinter.addText("(High Class Veg Restaurant) \n");

                method = "addTextSize";
                mPrinter.addTextSize(1, 1);

                method = "addText";
                mPrinter.addText("Hotel City View, Opp New Bus Stand, \n");

                method = "addTextSize";
                mPrinter.addTextSize(1, 1);

                method = "addText";
                mPrinter.addText("Salem - 4 \n");

                method = "addTextSize";
                mPrinter.addTextSize(1, 1);

                method = "addText";
                mPrinter.addText("Ph: 0427-2433702 \n");

                method = "addTextSize";
                mPrinter.addTextSize(1, 1);

                method = "addText";
                mPrinter.addText("Date :" + Date1 + " Time : " + mTime + "\n");

                method = "addTextSize";
                mPrinter.addTextSize(1, 1);
                textData.append(".........................................\n");

                method = "addText";
                mPrinter.addText(textData.toString());
                textData.delete(0, textData.length());

                method = "addFeedLine";
                mPrinter.addFeedLine(1);

                method = "addTextSize";
                mPrinter.addTextSize(1, 1);

                if (mIsParcel) {
                    mPrinter.addText("Parcel Meals      - ");
                } else {
                    mPrinter.addText("Full Meals       - ");
                }

                method = "addTextSize";
                mPrinter.addTextSize(2, 2);

                method = "addText";

                if (mIsParcel) {
                    mPrinter.addText(" KOT " + mTotalCount + " \n");
                } else {
                    mPrinter.addText(" KOT " + mTotalCount + " \n");
                }

                method = "addFeedLine";
                mPrinter.addFeedLine(1);

                method = "addTextSize";
                mPrinter.addTextSize(1, 1);

                textData.append(".........................................\n");
                method = "addText";

                mPrinter.addText(textData.toString());
                textData.delete(0, textData.length());

                method = "addFeedLine";
                mPrinter.addFeedLine(4);

            }
        } catch (Exception e) {
            ShowMsg.showException(e, method, mContext);

            mTextFm.setEnabled(true);
            mTextFm.setBackgroundColor(Color.parseColor("#F95630"));

            mTextPm.setEnabled(true);
            mTextPm.setBackgroundColor(Color.parseColor("#00A083"));

            mProgressDialog.dismiss();
            System.out.println(" - - YES 1 - - " + String.valueOf(mPreferences.getPrintCount() - 1) + " - - NO - - " + mPreferences.getPrintCount());

            if (mIsParcel) {
                mPreferences.setPrintParcelCount(mPreferences.getPrintParcelCount() - Integer.parseInt(mTotalCount));
            } else {
                mPreferences.setPrintCount(mPreferences.getPrintCount() - Integer.parseInt(mTotalCount));
            }

            return false;
        }

        textData = null;

        return true;
    }

    private boolean printData() {
        if (mPrinter == null) {
            return false;
        }

        if (!connectPrinter()) {
            return false;
        }

        PrinterStatusInfo status = mPrinter.getStatus();

        if (!isPrintable(status)) {
            ShowMsg.showMsg(makeErrorMessage(status), mContext);

            mTextFm.setEnabled(true);
            mTextFm.setBackgroundColor(Color.parseColor("#F95630"));

            mTextPm.setEnabled(true);
            mTextPm.setBackgroundColor(Color.parseColor("#00A083"));

            mProgressDialog.dismiss();
            if (mIsParcel) {
                mPreferences.setPrintParcelCount(mPreferences.getPrintParcelCount() - Integer.parseInt(mTotalCount));
            } else {
                mPreferences.setPrintCount(mPreferences.getPrintCount() - Integer.parseInt(mTotalCount));
            }

            try {
                mPrinter.disconnect();
            } catch (Exception ex) {
                // Do nothing
            }
            return false;
        }

        try {
            mPrinter.sendData(Printer.PARAM_DEFAULT);
        } catch (Exception e) {
            ShowMsg.showException(e, "sendData", mContext);

            mTextFm.setEnabled(true);
            mTextFm.setBackgroundColor(Color.parseColor("#F95630"));

            mTextPm.setEnabled(true);
            mTextPm.setBackgroundColor(Color.parseColor("#00A083"));

            mProgressDialog.dismiss();
            System.out.println(" - - YES 1 - - " + String.valueOf(mPreferences.getPrintCount() - 1) + " - - NO - - " + mPreferences.getPrintCount());

            if (mIsParcel) {
                mPreferences.setPrintParcelCount(mPreferences.getPrintParcelCount() - Integer.parseInt(mTotalCount));
            } else {
                mPreferences.setPrintCount(mPreferences.getPrintCount() - Integer.parseInt(mTotalCount));
            }

            try {
                mPrinter.disconnect();
            } catch (Exception ex) {
                // Do nothing
            }
            return false;
        }

        return true;
    }

    private boolean initializeObject() {

        try {

            if (new AppPreferences().getPrinterModel() != -1 && new AppPreferences().getPrinterLang() != -1) {
                mPrinter = new Printer(new AppPreferences().getPrinterModel(), new AppPreferences().getPrinterLang(),
                        mContext);
            }

        } catch (Exception e) {
            ShowMsg.showException(e, "Printer", mContext);

            mTextFm.setEnabled(true);
            mTextFm.setBackgroundColor(Color.parseColor("#F95630"));

            mTextPm.setEnabled(true);
            mTextPm.setBackgroundColor(Color.parseColor("#00A083"));

            mProgressDialog.dismiss();

            System.out.println(" - - YES 1 - - " + String.valueOf(mPreferences.getPrintCount() - 1) + " - - NO - - " + mPreferences.getPrintCount());

            if (mIsParcel) {
                mPreferences.setPrintParcelCount(mPreferences.getPrintParcelCount() - Integer.parseInt(mTotalCount));
            } else {
                mPreferences.setPrintCount(mPreferences.getPrintCount() - Integer.parseInt(mTotalCount));
            }

            return false;
        }

        mPrinter.setReceiveEventListener(this);

        return true;
    }

    private void finalizeObject() {
        if (mPrinter == null) {
            return;
        }

        mPrinter.clearCommandBuffer();

        mPrinter.setReceiveEventListener(null);

        mPrinter = null;
    }

    private boolean connectPrinter() {
        boolean isBeginTransaction = false;

        if (mPrinter == null) {
            return false;
        }

        try {
            mPrinter.connect(mPreferences.getPrinterTarget(), Printer.PARAM_DEFAULT);
        } catch (Exception e) {
            ShowMsg.showException(e, "connect", mContext);

            mTextFm.setEnabled(true);
            mTextFm.setBackgroundColor(Color.parseColor("#F95630"));

            mTextPm.setEnabled(true);
            mTextPm.setBackgroundColor(Color.parseColor("#00A083"));

            mProgressDialog.dismiss();
            System.out.println(" - - YES 1 - - " + String.valueOf(mPreferences.getPrintCount() - 1) + " - - NO - - " + mPreferences.getPrintCount());

            if (mIsParcel) {
                mPreferences.setPrintParcelCount(mPreferences.getPrintParcelCount() - Integer.parseInt(mTotalCount));
            } else {
                mPreferences.setPrintCount(mPreferences.getPrintCount() - Integer.parseInt(mTotalCount));
            }
            return false;
        }

        try {
            mPrinter.beginTransaction();
            isBeginTransaction = true;
        } catch (Exception e) {
            ShowMsg.showException(e, "beginTransaction", mContext);

            mTextFm.setEnabled(true);
            mTextFm.setBackgroundColor(Color.parseColor("#F95630"));

            mTextPm.setEnabled(true);
            mTextPm.setBackgroundColor(Color.parseColor("#00A083"));

            mProgressDialog.dismiss();
            System.out.println(" - - YES 1 - - " + String.valueOf(mPreferences.getPrintCount() - 1) + " - - NO - - " + mPreferences.getPrintCount());

            if (mIsParcel) {
                mPreferences.setPrintParcelCount(mPreferences.getPrintParcelCount() - Integer.parseInt(mTotalCount));
            } else {
                mPreferences.setPrintCount(mPreferences.getPrintCount() - Integer.parseInt(mTotalCount));
            }

        }

        if (isBeginTransaction == false) {
            try {
                mPrinter.disconnect();
            } catch (Epos2Exception e) {
                // Do nothing
                return false;
            }
        }

        return true;
    }

    private void disconnectPrinter() {
        if (mPrinter == null) {
            return;
        }

        try {
            mPrinter.endTransaction();
        } catch (final Exception e) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public synchronized void run() {
                    ShowMsg.showException(e, "endTransaction", mContext);

                    mTextFm.setEnabled(true);
                    mTextFm.setBackgroundColor(Color.parseColor("#F95630"));

                    mTextPm.setEnabled(true);
                    mTextPm.setBackgroundColor(Color.parseColor("#00A083"));

                    mProgressDialog.dismiss();
                    System.out.println(" - - YES 1 - - " + String.valueOf(mPreferences.getPrintCount() - 1) + " - - NO - - " + mPreferences.getPrintCount());

                    if (mIsParcel) {
                        mPreferences.setPrintParcelCount(mPreferences.getPrintParcelCount() - Integer.parseInt(mTotalCount));
                    } else {
                        mPreferences.setPrintCount(mPreferences.getPrintCount() - Integer.parseInt(mTotalCount));
                    }

                }
            });
        }

        try {
            mPrinter.disconnect();
        } catch (final Exception e) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public synchronized void run() {
                    ShowMsg.showException(e, "disconnect", mContext);

                    mTextFm.setEnabled(true);
                    mTextFm.setBackgroundColor(Color.parseColor("#F95630"));

                    mTextPm.setEnabled(true);
                    mTextPm.setBackgroundColor(Color.parseColor("#00A083"));

                    mProgressDialog.dismiss();
                    System.out.println(" - - YES 1 - - " + String.valueOf(mPreferences.getPrintCount() - 1) + " - - NO - - " + mPreferences.getPrintCount());

                    if (mIsParcel) {
                        mPreferences.setPrintParcelCount(mPreferences.getPrintParcelCount() - Integer.parseInt(mTotalCount));
                    } else {
                        mPreferences.setPrintCount(mPreferences.getPrintCount() - Integer.parseInt(mTotalCount));
                    }
                }
            });
        }

        finalizeObject();
    }

    private boolean isPrintable(PrinterStatusInfo status) {
        if (status == null) {
            return false;
        }

        if (status.getConnection() == Printer.FALSE) {
            return false;
        } else if (status.getOnline() == Printer.FALSE) {
            return false;
        } else {
            ;//print available
        }

        return true;
    }

    private String makeErrorMessage(PrinterStatusInfo status) {
        String msg = "";

        if (status.getOnline() == Printer.FALSE) {
            msg += getString(R.string.handlingmsg_err_offline);
        }
        if (status.getConnection() == Printer.FALSE) {
            msg += getString(R.string.handlingmsg_err_no_response);
        }
        if (status.getCoverOpen() == Printer.TRUE) {
            msg += getString(R.string.handlingmsg_err_cover_open);
        }
        if (status.getPaper() == Printer.PAPER_EMPTY) {
            msg += getString(R.string.handlingmsg_err_receipt_end);
        }
        if (status.getPaperFeed() == Printer.TRUE || status.getPanelSwitch() == Printer.SWITCH_ON) {
            msg += getString(R.string.handlingmsg_err_paper_feed);
        }
        if (status.getErrorStatus() == Printer.MECHANICAL_ERR || status.getErrorStatus() == Printer.AUTOCUTTER_ERR) {
            msg += getString(R.string.handlingmsg_err_autocutter);
            msg += getString(R.string.handlingmsg_err_need_recover);
        }
        if (status.getErrorStatus() == Printer.UNRECOVER_ERR) {
            msg += getString(R.string.handlingmsg_err_unrecover);
        }
        if (status.getErrorStatus() == Printer.AUTORECOVER_ERR) {
            if (status.getAutoRecoverError() == Printer.HEAD_OVERHEAT) {
                msg += getString(R.string.handlingmsg_err_overheat);
                msg += getString(R.string.handlingmsg_err_head);
            }
            if (status.getAutoRecoverError() == Printer.MOTOR_OVERHEAT) {
                msg += getString(R.string.handlingmsg_err_overheat);
                msg += getString(R.string.handlingmsg_err_motor);
            }
            if (status.getAutoRecoverError() == Printer.BATTERY_OVERHEAT) {
                msg += getString(R.string.handlingmsg_err_overheat);
                msg += getString(R.string.handlingmsg_err_battery);
            }
            if (status.getAutoRecoverError() == Printer.WRONG_PAPER) {
                msg += getString(R.string.handlingmsg_err_wrong_paper);
            }
        }
        if (status.getBatteryLevel() == Printer.BATTERY_LEVEL_0) {
            msg += getString(R.string.handlingmsg_err_battery_real_end);
        }

        return msg;
    }

    @Override
    public void onPtrReceive(final Printer printerObj, final int code, final PrinterStatusInfo status, final String printJobId) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public synchronized void run() {

                if (mPreferences.getPrintType() == 2) {

                    if (mPrintCount == 1) {

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                disconnectPrinter();
                            }
                        }).start();

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                if (code == 0)
                                    mPrintCount = 2;

                                mTimeStamp = mSimpleDateFormat.format(mCalendar.getTime());

                                if (mIsParcel) {
                                    mPreferences.setPrintParcelCount(mPreferences.getPrintParcelCount() + Integer.parseInt(mTotalCount));
                                } else {
                                    mPreferences.setPrintCount(mPreferences.getPrintCount() + Integer.parseInt(mTotalCount));
                                }
//                            mHotelDatabase.InsertRecord(mPreferences.getName(), mTimeStamp, "" + 1);

                                runPrintReceiptSequence();
                            }
                        }, 3000);

                        return;
                    }

                    if (mPrintCount == 2) {
                        mProgressDialog.dismiss();
                        mTextFm.setEnabled(true);
                        mTextFm.setBackgroundColor(Color.parseColor("#F95630"));

                        mTextPm.setEnabled(true);
                        mTextPm.setBackgroundColor(Color.parseColor("#00A083"));

                        mTimeStamp = mSimpleDateFormat.format(mCalendar.getTime());
                        if (mIsParcel) {
                            mHotelDatabase.InsertRecord(mPreferences.getName(), Date1, "" + mTotalCount, "P", uniqueId);
                            mEditCount.setText("");
                        } else {
                            mHotelDatabase.InsertRecord(mPreferences.getName(), Date1, "" + mTotalCount, "L", uniqueId);
                            mEditCount.setText("");
                        }

//                    ShowMsg.showResult(code, makeErrorMessage(status), mContext);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                disconnectPrinter();
                            }
                        }).start();
                    }
                } else {

                    mProgressDialog.dismiss();
                    mTextFm.setEnabled(true);
                    mTextFm.setBackgroundColor(Color.parseColor("#F95630"));

                    mTextPm.setEnabled(true);
                    mTextPm.setBackgroundColor(Color.parseColor("#00A083"));

                    mPrintCount = 1;

                    mTimeStamp = mSimpleDateFormat.format(mCalendar.getTime());

                    if (mIsParcel) {
                        mHotelDatabase.InsertRecord(mPreferences.getName(), Date1, "" + mTotalCount, "P", uniqueId);
                        mEditCount.setText("");
                    } else {
                        mHotelDatabase.InsertRecord(mPreferences.getName(), Date1, "" + mTotalCount, "L", uniqueId);
                        mEditCount.setText("");
                    }

//                    ShowMsg.showResult(code, makeErrorMessage(status), mContext);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            disconnectPrinter();
                        }
                    }).start();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.txt_1:
                setcount("1");
                break;

            case R.id.txt_2:
                setcount("2");
                break;

            case R.id.txt_3:
                setcount("3");
                break;

            case R.id.txt_4:
                setcount("4");
                break;

            case R.id.txt_5:
                setcount("5");
                break;

            case R.id.txt_6:
                setcount("6");
                break;

            case R.id.txt_7:
                setcount("7");
                break;

            case R.id.txt_8:
                setcount("8");
                break;

            case R.id.txt_9:
                setcount("9");
                break;

            case R.id.txt_0:

//                if (mEditCount.getText().toString().equals("00")) {
//                    mEditCount.setText("0");
//                } else {
                setcount("0");
//                }

                break;

            case R.id.btn_clear:
                mEditCount.setText("");
                mTotalCount = "0";
                break;
        }
    }

    private String GetUUID() {

        String _id = UUID.randomUUID().toString();

        String[] qq = _id.split("-");

        uniqueId = qq[0].substring(4);

        return uniqueId;
    }

    private void setcount(String _count) {

        if (mEditCount != null) {

            if (mEditCount.getText().toString().length() != 0 && mEditCount.getText().toString().charAt(0) == '0' && _count.equals("0")) {
                mEditCount.setText("0");
            } else {

                if (mEditCount.getText().toString().length() != 0 && mEditCount.getText().toString().charAt(0) == '0') {
                    mEditCount.setText("");
                }
                mTotalCount = (new StringBuilder()).append(_count).toString();
                mEditCount.append(mTotalCount);
                mTotalCount = mEditCount.getText().toString();
            }

        }
    }
}