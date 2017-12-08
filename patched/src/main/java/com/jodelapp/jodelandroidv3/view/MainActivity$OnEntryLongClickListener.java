package com.jodelapp.jodelandroidv3.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.jodelapp.jodelandroidv3.jp.TSnackbar;

/**
 * Created by Admin on 07.12.2017.
 */

public class MainActivity$OnEntryLongClickListener implements View.OnLongClickListener {

    private int index;
    private AlertDialog mAlertDialog;

    public MainActivity$OnEntryLongClickListener(int index, AlertDialog mAlertDialog) {
        this.index = index;
        this.mAlertDialog = mAlertDialog;
    }

    @Override
    public boolean onLongClick(View v) {
        try {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setClassName("io.github.krokofant.placepickerproxy", "io.github.krokofant.placepickerproxy.MainActivity");
            sharingIntent.putExtra("fast_location_picker",true);
            sharingIntent.putExtra("fast_location_picker_slot", index);
            Toast.makeText(mAlertDialog.getContext(), "Starting location picker...", Toast.LENGTH_LONG).show();
            MainActivity.staticActivity.startActivityForResult(sharingIntent, 108);
            mAlertDialog.dismiss();
        } catch (IllegalArgumentException e) {
            TSnackbar.make("Please install the JodelTools first!");
        }
        return true;
    }
}
