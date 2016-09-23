package com.example.android.myapplication.Utils;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.wifi.WifiManager;

import com.example.android.myapplication.AppController;

/**
 * Created by android on 25/8/16.
 */

public class WiFiBTStatus {

    private Context mContext;

    private WifiManager mWifiManager;
    private BluetoothAdapter mBluetoothAdapter;

    public WiFiBTStatus() {

        this.mContext = AppController.getInstance();

        this.mWifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    }

    public boolean GetWiFiStatus() {
        return this.mWifiManager.isWifiEnabled();
    }

    public boolean isBluetoothAvailable() {
        return (this.mBluetoothAdapter != null && this.mBluetoothAdapter.isEnabled());
    }

    public boolean SetWiFiStatus(boolean _status) {

        boolean isEnabled = GetWiFiStatus();

        if (_status && !isEnabled) {
            return this.mWifiManager.setWifiEnabled(true);
        } else if (!_status && isEnabled) {
            return this.mWifiManager.setWifiEnabled(false);
        }

        return true;
    }

    public boolean SetBluetoothStatus(boolean _status) {

        boolean isEnabled = isBluetoothAvailable();

        if (_status && !isEnabled) {
            return this.mBluetoothAdapter.enable();
        } else if (!_status && isEnabled) {
            return this.mBluetoothAdapter.disable();
        }

        return true;
    }
}
