package com.example.android.myapplication.UI;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.Window;

import com.example.android.myapplication.Preferences.AppPreferences;
import com.example.android.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.ACCESS_WIFI_STATE;
import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class SplashScreen extends Activity {

    private static final int REQUEST_CALL = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash_screen);

        if (!mayRequestCall()) {
            return;
        } else {

            if (!new AppPreferences().getIsLogin()) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                        finish();
                    }
                }, 3000);
            } else {
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
            }
        }
    }

    private boolean mayRequestCall() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        final String[] perms = {ACCESS_WIFI_STATE, ACCESS_NETWORK_STATE, ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION};

        int permission1 = checkSelfPermission(ACCESS_WIFI_STATE);
        int permission2 = checkSelfPermission(ACCESS_NETWORK_STATE);
        int permission3 = checkSelfPermission(ACCESS_COARSE_LOCATION);
        int permission4 = checkSelfPermission(ACCESS_FINE_LOCATION);

        System.out.println("cameraPermission " + permission1 + permission2 + permission3 + permission4);
        StringBuffer msgtext = new StringBuffer();
        final List<String> listPermissionsNeeded = new ArrayList<>();
        if (permission1 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(ACCESS_WIFI_STATE);
            msgtext.append("Wi-Fi State,");
        }
        if (permission2 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(ACCESS_NETWORK_STATE);
            msgtext.append("Network State");
        }
        if (permission3 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(ACCESS_COARSE_LOCATION);
            msgtext.append("BlueTooth Access,");
        }

        if (permission4 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(ACCESS_FINE_LOCATION);
            msgtext.append("BlueTooth Access,");
        }

        if (!listPermissionsNeeded.isEmpty()) {

            System.out.println("checkSelfPermission false");
            if (shouldShowRequestPermissionRationale(ACCESS_WIFI_STATE) || shouldShowRequestPermissionRationale(ACCESS_NETWORK_STATE)
                    || shouldShowRequestPermissionRationale(ACCESS_COARSE_LOCATION) || shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                System.out.println("shouldShowRequestPermissionRationale true");
                System.out.println("shouldShowRequestPermissionRationale true");
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SplashScreen.this);
                alertDialogBuilder.setCancelable(false);
                alertDialogBuilder.setTitle("Permission Alert !").setMessage("Following Permission's Required");
                alertDialogBuilder.setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @TargetApi(Build.VERSION_CODES.M)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        requestPermissions(listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_CALL);
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            } else {
                System.out.println("shouldShowRequestPermissionRationale false");
                requestPermissions(listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_CALL);
            }

        } else {
            System.out.println("checkSelfPermission true");
            return true;
        }
        System.out.println("return false");
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            int j = 0;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    j++;
                }
            }
            System.out.println("i j  false " + j + "  -  ");

            if (grantResults.length == j) {

                if (!new AppPreferences().getIsLogin()) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(SplashScreen.this, LoginActivity.class));
                            finish();
                        }
                    }, 3000);
                } else {
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                }

            } else {
                mayRequestCall();
            }
        }
    }

//    private void populateAutoComplete() {
//
//        if (!mayRequestPermissions()) {
//            return;
//        }
//
//        if (!new AppPreferences().getIsLogin()) {
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    startActivity(new Intent(SplashScreen.this, LoginActivity.class));
//                    finish();
//                }
//            }, 3000);
//        } else {
//            startActivity(new Intent(SplashScreen.this, MainActivity.class));
//        }
//    }
}
