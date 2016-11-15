package com.example.android.myapplication.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.Log;
import com.epson.epos2.printer.Printer;
import com.epson.epos2.printer.PrinterStatusInfo;
import com.epson.epos2.printer.ReceiveListener;
import com.example.android.myapplication.Model.SpnModels;
import com.example.android.myapplication.Preferences.AppPreferences;
import com.example.android.myapplication.R;
import com.example.android.myapplication.Utils.ShowMsg;
import com.example.android.myapplication.Utils.WiFiBTStatus;

public class SettingsActivity extends Activity implements View.OnClickListener, ReceiveListener {

    private Context mContext = null;
    private EditText mEditTarget = null;
    private Spinner mSpnSeries = null;
    private Spinner mSpnLang = null;
    private Printer mPrinter = null;

    private RadioGroup mRadioGroup;
    private RadioButton mRadioType;

    private AppPreferences mPreferences;

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mContext = this;
        mPreferences = new AppPreferences();

        mButton = (Button) findViewById(R.id.btn_save);

        mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        int[] target = {
                R.id.btnDiscovery,
                R.id.btnSampleReceipt,
                R.id.btnSampleCoupon
        };

        for (int i = 0; i < target.length; i++) {
            Button button = (Button) findViewById(target[i]);
            button.setOnClickListener(this);
        }

        mEditTarget = (EditText) findViewById(R.id.edtTarget);

        mSpnSeries = (Spinner) findViewById(R.id.spnModel);
        ArrayAdapter<SpnModels> seriesAdapter = new ArrayAdapter<SpnModels>(this, android.R.layout.simple_spinner_item);
        seriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        seriesAdapter.add(new SpnModels(getString(R.string.printerseries_m10), Printer.TM_M10));
        seriesAdapter.add(new SpnModels(getString(R.string.printerseries_m30), Printer.TM_M30));
        seriesAdapter.add(new SpnModels(getString(R.string.printerseries_p20), Printer.TM_P20));
        seriesAdapter.add(new SpnModels(getString(R.string.printerseries_p60), Printer.TM_P60));
        seriesAdapter.add(new SpnModels(getString(R.string.printerseries_p60ii), Printer.TM_P60II));
        seriesAdapter.add(new SpnModels(getString(R.string.printerseries_p80), Printer.TM_P80));
        seriesAdapter.add(new SpnModels(getString(R.string.printerseries_t20), Printer.TM_T20));
        seriesAdapter.add(new SpnModels(getString(R.string.printerseries_t60), Printer.TM_T60));
        seriesAdapter.add(new SpnModels(getString(R.string.printerseries_t70), Printer.TM_T70));
        seriesAdapter.add(new SpnModels(getString(R.string.printerseries_t81), Printer.TM_T81));
        seriesAdapter.add(new SpnModels(getString(R.string.printerseries_t82), Printer.TM_T82));
        seriesAdapter.add(new SpnModels(getString(R.string.printerseries_t83), Printer.TM_T83));
        seriesAdapter.add(new SpnModels(getString(R.string.printerseries_t88), Printer.TM_T88));
        seriesAdapter.add(new SpnModels(getString(R.string.printerseries_t90), Printer.TM_T90));
        seriesAdapter.add(new SpnModels(getString(R.string.printerseries_t90kp), Printer.TM_T90KP));
        seriesAdapter.add(new SpnModels(getString(R.string.printerseries_u220), Printer.TM_U220));
        seriesAdapter.add(new SpnModels(getString(R.string.printerseries_u330), Printer.TM_U330));
        seriesAdapter.add(new SpnModels(getString(R.string.printerseries_l90), Printer.TM_L90));
        seriesAdapter.add(new SpnModels(getString(R.string.printerseries_h6000), Printer.TM_H6000));
        mSpnSeries.setAdapter(seriesAdapter);
        mSpnSeries.setSelection(0);

        mSpnLang = (Spinner) findViewById(R.id.spnLang);
        ArrayAdapter<SpnModels> langAdapter = new ArrayAdapter<SpnModels>(this, android.R.layout.simple_spinner_item);
        langAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        langAdapter.add(new SpnModels(getString(R.string.lang_ank), Printer.MODEL_ANK));
        langAdapter.add(new SpnModels(getString(R.string.lang_japanese), Printer.MODEL_JAPANESE));
        langAdapter.add(new SpnModels(getString(R.string.lang_chinese), Printer.MODEL_CHINESE));
        langAdapter.add(new SpnModels(getString(R.string.lang_taiwan), Printer.MODEL_TAIWAN));
        langAdapter.add(new SpnModels(getString(R.string.lang_korean), Printer.MODEL_KOREAN));
        langAdapter.add(new SpnModels(getString(R.string.lang_thai), Printer.MODEL_THAI));
        langAdapter.add(new SpnModels(getString(R.string.lang_southasia), Printer.MODEL_SOUTHASIA));
        mSpnLang.setAdapter(langAdapter);
        mSpnLang.setSelection(0);

        try {
            Log.setLogSettings(mContext, Log.PERIOD_TEMPORARY, Log.OUTPUT_STORAGE, null, 0, 1, Log.LOGLEVEL_LOW);
        } catch (Exception e) {
            ShowMsg.showException(e, "setLogSettings", mContext);
        }

        mSpnSeries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mPreferences.setPrinterModel(((SpnModels) mSpnSeries.getSelectedItem()).getModelConstant());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSpnLang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mPreferences.setPrinterLang(((SpnModels) mSpnLang.getSelectedItem()).getModelConstant());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get selected radio button from radioGroup
                int selectedId = mRadioGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                mRadioType = (RadioButton) findViewById(selectedId);

                if (mRadioType.getText().toString().equals("1")) {
                    mPreferences.setPrintType(1);
                } else {
                    mPreferences.setPrintType(1);
                }

//                Toast.makeText(mContext, mRadioType.getText(), Toast.LENGTH_SHORT).show();

                finish();
            }
        });

        /**
         * Set Old Values
         */
        mEditTarget.setText(mPreferences.getPrinterTarget());
        mSpnSeries.setSelection(mPreferences.getPrinterModel());
        mSpnLang.setSelection(mPreferences.getPrinterLang());

        if (mPreferences.getPrintType() == 1) {
            mRadioType = (RadioButton) findViewById(R.id.radio_one);
            mRadioGroup.check(R.id.radio_one);
        } else {
            mRadioType = (RadioButton) findViewById(R.id.radio_one);
            mRadioGroup.check(R.id.radio_one);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, final int resultCode, final Intent data) {

        if (data != null && resultCode == RESULT_OK) {

            String target = data.getStringExtra(getString(R.string.title_target));

            if (target != null) {

                mPreferences.setPrinterTarget(target);

                if (mEditTarget != null)
                    mEditTarget.setText(target);

                if (mEditTarget != null && mEditTarget.toString().length() == 0) {
                    mEditTarget.setText(mPreferences.getPrinterTarget());
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;

        switch (v.getId()) {
            case R.id.btnDiscovery:

                intent = new Intent(this, DiscoveryActivity.class);
                startActivityForResult(intent, 0);
                break;

            case R.id.btnSampleReceipt:
                updateButtonState(false);
                if (!runPrintReceiptSequence()) {
                    updateButtonState(true);
                }
                break;

            default:
                // Do nothing
                break;
        }
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
            mPrinter.addText("SHREE \n");

            method = "addTextSize";
            mPrinter.addTextSize(2, 2);

            method = "addText";
            mPrinter.addText("SRISARAVANAHAVAN \n");

            method = "addFeedLine";
            mPrinter.addFeedLine(1);

            method = "addTextSize";
            mPrinter.addTextSize(1, 1);

            method = "addText";
            mPrinter.addText("\"" + "classic" + "\" \n");

            method = "addTextSize";
            mPrinter.addTextSize(1, 1);

            method = "addText";
            mPrinter.addText("Salem - 4 \n");

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
            mPrinter.addText("Full Meals       - KOT " + " COUNT " + " \n");

            method = "addFeedLine";
            mPrinter.addFeedLine(1);

            method = "addTextSize";
            mPrinter.addTextSize(1, 1);

            textData.append(".........................................\n");
            method = "addText";
            mPrinter.addText(textData.toString());
            textData.delete(0, textData.length());

            method = "addFeedLine";
            mPrinter.addFeedLine(2);

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

        dispPrinterWarnings(status);

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

            if (mPreferences.getPrinterModel() != -1 && mPreferences.getPrinterLang() != -1) {
                mPrinter = new Printer(mPreferences.getPrinterModel(), mPreferences.getPrinterLang(),
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
            mPrinter.connect(mEditTarget.getText().toString(), Printer.PARAM_DEFAULT);
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

    private void dispPrinterWarnings(PrinterStatusInfo status) {
        EditText edtWarnings = (EditText) findViewById(R.id.edtWarnings);
        String warningsMsg = "";

        if (status == null) {
            return;
        }

        if (status.getPaper() == Printer.PAPER_NEAR_END) {
            warningsMsg += getString(R.string.handlingmsg_warn_receipt_near_end);
        }

        if (status.getBatteryLevel() == Printer.BATTERY_LEVEL_1) {
            warningsMsg += getString(R.string.handlingmsg_warn_battery_near_end);
        }

        edtWarnings.setText(warningsMsg);
    }

    private void updateButtonState(boolean state) {
        Button btnReceipt = (Button) findViewById(R.id.btnSampleReceipt);
        Button btnCoupon = (Button) findViewById(R.id.btnSampleCoupon);
        btnReceipt.setEnabled(state);
        btnCoupon.setEnabled(state);
    }

    @Override
    public void onPtrReceive(final Printer printerObj, final int code, final PrinterStatusInfo status, final String printJobId) {
        runOnUiThread(new Runnable() {
            @Override
            public synchronized void run() {
                ShowMsg.showResult(code, makeErrorMessage(status), mContext);

                dispPrinterWarnings(status);

                updateButtonState(true);

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
