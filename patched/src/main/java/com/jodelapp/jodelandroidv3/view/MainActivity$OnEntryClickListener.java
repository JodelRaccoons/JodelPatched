package com.jodelapp.jodelandroidv3.view;

import android.app.AlertDialog;
import android.view.View;

import com.google.android.gms.maps.model.LatLng;
import com.jodelapp.jodelandroidv3.jp.JPStorage;
import com.jodelapp.jodelandroidv3.jp.JPUtils;

import java.util.Map;

/**
 * Created by Admin on 07.12.2017.
 */

public class MainActivity$OnEntryClickListener implements View.OnClickListener{

    private JPStorage mStorage;
    private Map.Entry<String,LatLng> mEntry;
    private AlertDialog mAlertdialog;

    public MainActivity$OnEntryClickListener(JPStorage mStorage, Map.Entry<String,LatLng> mEntry, AlertDialog mAlertdialog) {
        this.mStorage = mStorage;
        this.mEntry = mEntry;
        this.mAlertdialog = mAlertdialog;
    }

    @Override
    public void onClick(View v) {
        mStorage.setSpoofLocation(mEntry.getValue().latitude, mEntry.getValue().longitude);
        mStorage.isSpoofLocation(true);
        JPUtils.updateJodelLocation();
        mAlertdialog.dismiss();
    }
}
