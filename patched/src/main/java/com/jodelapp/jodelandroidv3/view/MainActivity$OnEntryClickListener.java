package com.jodelapp.jodelandroidv3.view;

import android.app.AlertDialog;
import android.location.Address;
import android.view.View;
import android.widget.Toast;

import com.jodelapp.jodelandroidv3.jp.JPStorage;
import com.jodelapp.jodelandroidv3.jp.JPUtils;

/**
 * Created by Admin on 07.12.2017.
 */

public class MainActivity$OnEntryClickListener implements View.OnClickListener{

    private JPStorage mStorage;
    private Address mEntry;
    private AlertDialog mAlertdialog;

    public MainActivity$OnEntryClickListener(JPStorage mStorage, Address mEntry, AlertDialog mAlertdialog) {
        this.mStorage = mStorage;
        this.mEntry = mEntry;
        this.mAlertdialog = mAlertdialog;
    }

    @Override
    public void onClick(View v) {
        if (mStorage != null && mEntry != null && mAlertdialog != null) {
            mStorage.setSpoofLocation(mEntry.getLatitude(), mEntry.getLongitude());
            mStorage.setSpoofLocation(true);
            JPUtils.updateJodelLocation();
            //JPLocationManager.updateLocation(mEntry);
            mAlertdialog.dismiss();
        } else if (mAlertdialog != null) {
            Toast.makeText(mAlertdialog.getContext(), "Something went wrong, please report to the devs!", Toast.LENGTH_LONG).show();

        }
    }
}
