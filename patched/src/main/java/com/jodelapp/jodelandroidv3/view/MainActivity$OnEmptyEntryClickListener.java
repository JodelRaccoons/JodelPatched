package com.jodelapp.jodelandroidv3.view;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Admin on 07.12.2017.
 */

public class MainActivity$OnEmptyEntryClickListener implements View.OnClickListener {

    private Activity mActivity;

    public MainActivity$OnEmptyEntryClickListener(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(mActivity, "Please set a location first by long-pressing", Toast.LENGTH_LONG).show();
    }
}
