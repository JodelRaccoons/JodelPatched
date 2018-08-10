package com.jodelapp.jodelandroidv3.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jodelapp.jodelandroidv3.jp.JPStorage;
import com.jodelapp.jodelandroidv3.jp.TSnackbar;

import static com.jodelapp.jodelandroidv3.jp.JPUtils.dpToPx;

/**
 * Created by Admin on 07.12.2017.
 */

public class MainActivity$OnEntryLongClickListener implements View.OnLongClickListener {

    private int index;
    private AlertDialog mAlertDialog;
    private JPStorage mStorage;
    private AlertDialog mSecondDialog;
    private View currentView;

    public MainActivity$OnEntryLongClickListener(int index, AlertDialog mAlertDialog, JPStorage mStorage, View currentView) {
        this.index = index;
        this.mAlertDialog = mAlertDialog;
        this.mStorage = mStorage;
        this.currentView = currentView;
    }

    @Override
    public boolean onLongClick(View v) {
        LinearLayout mRootLL = new LinearLayout(mAlertDialog.getContext());
        mRootLL.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams mRootLLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mRootLL.setLayoutParams(mRootLLP);

        LinearLayout.LayoutParams subLLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        subLLP.gravity = Gravity.CENTER;
        subLLP.setMargins(0, dpToPx(12), 0, dpToPx(12));

        TextView tvDeleteEntry = new TextView(mAlertDialog.getContext());
        tvDeleteEntry.setGravity(Gravity.CENTER);
        tvDeleteEntry.setText("Delete entry");
        tvDeleteEntry.setOnClickListener(new OnDeleteLocationClickListener());

        tvDeleteEntry.setLayoutParams(subLLP);
        mRootLL.addView(tvDeleteEntry);

        View divider = new View(mAlertDialog.getContext());
        LinearLayout.LayoutParams dividerLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3);
        divider.setBackgroundColor(Color.LTGRAY);
        divider.setLayoutParams(dividerLayoutParams);
        mRootLL.addView(divider);

        TextView tvSetLocation = new TextView(mAlertDialog.getContext());
        tvSetLocation.setGravity(Gravity.CENTER);
        tvSetLocation.setText("Set location");
        tvSetLocation.setOnClickListener(new OnSetLocationClickListener());

        tvSetLocation.setLayoutParams(subLLP);
        mRootLL.addView(tvSetLocation);


        mSecondDialog = new AlertDialog.Builder(mAlertDialog.getContext())
                .setView(mRootLL)
                .show();


        return true;
    }

    private class OnDeleteLocationClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            mStorage.deleteLocationAtIndex(index);
            mStorage.setNumFastSpoofLocations(mStorage.getNumFastSpoofLocations() - 1);
            mSecondDialog.dismiss();

            LinearLayout rootLL = LinearLayout.class.cast(currentView.getParent());
            int viewIndex = rootLL.indexOfChild(currentView);
            rootLL.removeViewAt(viewIndex + 1);
            rootLL.removeViewAt(viewIndex);
        }
    }

    private class OnSetLocationClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            try {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setClassName("io.github.krokofant.placepickerproxy", "io.github.krokofant.placepickerproxy.MainActivity");
                sharingIntent.putExtra("fast_location_picker", true);
                sharingIntent.putExtra("fast_location_picker_slot", index);
                Toast.makeText(mAlertDialog.getContext(), "Starting location picker...", Toast.LENGTH_LONG).show();
                MainActivity.staticActivity.startActivityForResult(sharingIntent, 108);
                mSecondDialog.dismiss();
                mAlertDialog.dismiss();
            } catch (Exception e) {
                TSnackbar.make("Please install the JodelTools first!");
            }
        }
    }
}
