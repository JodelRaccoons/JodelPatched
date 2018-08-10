package com.jodelapp.jodelandroidv3.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.location.Address;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jodelapp.jodelandroidv3.jp.JPStorage;

import static com.jodelapp.jodelandroidv3.jp.JPUtils.dpToPx;

/**
 * Created by Admin on 08.04.2018.
 */

public class MainActivity$OnAddLocationClickListener implements View.OnClickListener {

    private LinearLayout mLocationsLL;
    private Activity mainActivity;
    private JPStorage mStorage;
    private int index;
    private AlertDialog mAlertDialog;

    public MainActivity$OnAddLocationClickListener(LinearLayout mLocationsLL, Activity mainActivity, JPStorage mStorage, AlertDialog mAlertDialog) {
        this.mLocationsLL = mLocationsLL;
        this.mainActivity = mainActivity;
        this.mStorage = mStorage;
        this.mAlertDialog = mAlertDialog;
    }

    @Override
    public void onClick(View view) {
        mStorage.setNumFastSpoofLocations(mStorage.getNumFastSpoofLocations() + 1);

        index = mStorage.getNumFastSpoofLocations() + 1;

        LinearLayout.LayoutParams subLLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        subLLP.gravity = Gravity.CENTER;
        subLLP.setMargins(0, dpToPx(12), 0, dpToPx(12));

        TextView textView = new TextView(mainActivity);
        textView.setGravity(Gravity.CENTER);

        final Address mEntry = mStorage.getFastLocationSpoof(index);
        if (mEntry != null) {
            textView.setText(mEntry.getLocality());
            textView.setOnClickListener(new MainActivity$OnEntryClickListener(mStorage, mEntry, mAlertDialog));
        } else {
            textView.setText("Not set");
            textView.setOnClickListener(new MainActivity$OnEmptyEntryClickListener(mainActivity));
        }

        textView.setOnLongClickListener(new MainActivity$OnEntryLongClickListener(index + 1, mAlertDialog, mStorage, textView));

        textView.setLayoutParams(subLLP);
        int index = mLocationsLL.indexOfChild(mLocationsLL.findViewWithTag("addImageView"));
        mLocationsLL.addView(textView, index);

        View divider = new View(mainActivity);
        LinearLayout.LayoutParams dividerLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3);
        divider.setBackgroundColor(Color.LTGRAY);
        divider.setLayoutParams(dividerLayoutParams);
        mLocationsLL.addView(divider, index + 1);
    }
}
