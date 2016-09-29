package com.example.android.myapplication.UI;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.printer.Printer;
import com.epson.epos2.printer.PrinterStatusInfo;
import com.epson.epos2.printer.ReceiveListener;
import com.example.android.myapplication.Database.HotelDatabase;
import com.example.android.myapplication.Preferences.AppPreferences;
import com.example.android.myapplication.R;
import com.example.android.myapplication.Utils.ShowMsg;
import com.example.android.myapplication.Utils.WiFiBTStatus;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by VijayarajSekar on 8/31/2016.
 */
public class PrintDailyCount extends AppCompatActivity implements ReceiveListener {

    private String TAG = PrintDailyCount.class.getSimpleName();

    private Context mContext;

    private Button mPrintToken;

    private WiFiBTStatus mWiFiBTStatus;

    private Printer mPrinter = null;

    private ProgressDialog mProgressDialog;

    private Calendar mCalendar;

    private SimpleDateFormat mSimpleDateFormat;

    private String mTimeStamp, mDate, mTime;

    private String[] mTimeStampTemp;

    private String mSno, mQty;

    private TextView mTextSno, mTextQty, mTextKot;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_print_daily_count);

        mContext = this;

        if (getIntent().getExtras() != null) {
            mSno = getIntent().getExtras().getString("SNO");
            mQty = getIntent().getExtras().getString("QTY");
        }

        mWiFiBTStatus = new WiFiBTStatus();

        mPrintToken = (Button) findViewById(R.id.btnprint);

        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Printing");

        mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
        mCalendar = Calendar.getInstance();

        mTimeStamp = mSimpleDateFormat.format(mCalendar.getTime());

        mTextSno = (TextView) findViewById(R.id.text_sno);
        mTextSno.setText(mSno);

        mTextQty = (TextView) findViewById(R.id.text_qty);
        mTextQty.setText(mQty);

        mTextKot = (TextView) findViewById(R.id.text_kot);
        mTextKot.setText("(KOT) - " + mQty);

        mTimeStampTemp = mTimeStamp.split(" ");
        mDate = mTimeStampTemp[0];
        mTime = mTimeStampTemp[1];

        mPrintToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mWiFiBTStatus.isBluetoothAvailable()) {

                    mPrintToken.setEnabled(false);
                    mProgressDialog.show();

                    if (new AppPreferences().getPrinterModel() != -1) {
                        runPrintReceiptSequence();
                    } else {
                        ShowMsg.showMsg("Printer setting is not available.", mContext);
                    }

                } else {
                    ShowMsg.showMsg("Enabling Bluetooth", mContext);
                    mWiFiBTStatus.SetBluetoothStatus(true);
                }
            }
        });
    }

    public boolean runPrintReceiptSequence() {

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
            method = "addTextAlign";
            mPrinter.addTextAlign(Printer.ALIGN_CENTER);

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
            mPrinter.addText("srisaravanabhavan \n");

//            method = "addTextSize";
//            mPrinter.addTextSize(2, 2);
//
//            method = "addText";
//            mPrinter.addText("SRISARAVANAHAVAN \n");

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

//            method = "addTextSize";
//            mPrinter.addTextSize(1, 1);
//
//            method = "addText";
//            mPrinter.addText("Opp New Bus Stand, \n");

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
            textData.append(".........................................\n");

            method = "addText";
            mPrinter.addText(textData.toString());
            textData.delete(0, textData.length());

            method = "addFeedLine";
            mPrinter.addFeedLine(1);

            method = "addTextSize";
            mPrinter.addTextSize(1, 1);

            method = "addText";
            mPrinter.addText("Token By               Date : " + mDate + "\n");

            method = "addTextSize";
            mPrinter.addTextSize(1, 1);

            method = "addText";
            mPrinter.addText(new AppPreferences().getName() + "               Time : " + mTime + "\n");

            method = "addTextSize";
            mPrinter.addTextSize(1, 1);
            textData.append(".........................................\n");

            method = "addText";
            mPrinter.addText(textData.toString());
            textData.delete(0, textData.length());

//            method = "addFeedLine";
//            mPrinter.addFeedLine(1);

            method = "addTextSize";
            mPrinter.addTextSize(1, 1);

            method = "addText";
            mPrinter.addText("Sno              Item               Qty" + "\n");

            method = "addTextSize";
            mPrinter.addTextSize(1, 1);
            textData.append(".........................................\n");

            method = "addText";
            mPrinter.addText(textData.toString());
            textData.delete(0, textData.length());

            method = "addTextSize";
            mPrinter.addTextSize(1, 1);

            method = "addFeedLine";
            mPrinter.addFeedLine(1);

            method = "addText";
            mPrinter.addText(mSno + "           Lunch Token             " + mQty + "\n");

            method = "addText";
            mPrinter.addText(textData.toString());
            textData.delete(0, textData.length());

            method = "addFeedLine";
            mPrinter.addFeedLine(3);

            method = "addText";
            mPrinter.addTextSize(2, 2);
            mPrinter.addText(" (KOT) -  " + mQty + " \n");

            method = "addText";
            mPrinter.addText(textData.toString());
            textData.delete(0, textData.length());

            method = "addFeedLine";
            mPrinter.addFeedLine(6);

        } catch (Exception e) {
            ShowMsg.showException(e, method, mContext);
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
            mPrinter.connect(new AppPreferences().getPrinterTarget(), Printer.PARAM_DEFAULT);
        } catch (Exception e) {
            ShowMsg.showException(e, "connect", mContext);
            return false;
        }

        try {
            mPrinter.beginTransaction();
            isBeginTransaction = true;
        } catch (Exception e) {
            ShowMsg.showException(e, "beginTransaction", mContext);
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
            runOnUiThread(new Runnable() {
                @Override
                public synchronized void run() {
                    ShowMsg.showException(e, "endTransaction", mContext);
                }
            });
        }

        try {
            mPrinter.disconnect();
        } catch (final Exception e) {
            runOnUiThread(new Runnable() {
                @Override
                public synchronized void run() {
                    ShowMsg.showException(e, "disconnect", mContext);
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

        runOnUiThread(new Runnable() {
            @Override
            public synchronized void run() {

                mProgressDialog.dismiss();
                mPrintToken.setEnabled(true);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        disconnectPrinter();
                    }
                }).start();

            }
        });
    }
}
