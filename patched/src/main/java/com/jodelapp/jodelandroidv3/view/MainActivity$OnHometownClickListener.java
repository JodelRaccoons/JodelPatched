package com.jodelapp.jodelandroidv3.view;

import android.app.AlertDialog;
import android.view.View;

/**
 * Created by Admin on 21.01.2018.
 */

public class MainActivity$OnHometownClickListener implements View.OnClickListener {

    private MainActivity mMainActivity;
    private View mView;
    private AlertDialog mAlertDialog;

    public MainActivity$OnHometownClickListener(MainActivity mMainActivity, View mView, AlertDialog mAlertDialog) {
        this.mMainActivity = mMainActivity;
        this.mView = mView;
        this.mAlertDialog = mAlertDialog;
    }

    @Override
    public void onClick(View view) {
        mAlertDialog.dismiss();
        mMainActivity.source_lambda$setupHomeTownTab$5(mMainActivity, mView);
    }
}
