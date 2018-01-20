package com.jodelapp.jodelandroidv3.features.create_text_post;

import android.app.AlertDialog;
import android.view.View;

import com.jodelapp.jodelandroidv3.jp.JPUtils;

/**
 * Created by Admin on 04.01.2018.
 */

public class CreateTextPostFragment$OnColorChoosenListener implements View.OnClickListener{

    private AlertDialog alertDialog;
    private CreateTextPostFragment mInstance;

    CreateTextPostFragment$OnColorChoosenListener(AlertDialog alertDialog, CreateTextPostFragment mInstance) {
        this.alertDialog = alertDialog;
        this.mInstance = mInstance;
    }

    @Override
    public void onClick(View v) {
        CreateTextPostPresenter mPresenter = (CreateTextPostPresenter) mInstance.presenter;

        final String tag = (String) v.getTag();
        switch (tag) {
            case "cp_orange":
                mInstance.setContainerBackgroundColor(JPUtils.Colors.Colors.get(0));
                mPresenter.setPostColor(JPUtils.Colors.Colors.get(0));
                break;
            case "cp_yellow":
                mInstance.setContainerBackgroundColor(JPUtils.Colors.Colors.get(1));
                mPresenter.setPostColor(JPUtils.Colors.Colors.get(1));
                break;
            case "cp_red":
                mInstance.setContainerBackgroundColor(JPUtils.Colors.Colors.get(2));
                mPresenter.setPostColor(JPUtils.Colors.Colors.get(2));
                break;
            case "cp_blue":
                mInstance.setContainerBackgroundColor(JPUtils.Colors.Colors.get(3));
                mPresenter.setPostColor(JPUtils.Colors.Colors.get(3));
                break;
            case "cp_bluegrayish":
                mInstance.setContainerBackgroundColor(JPUtils.Colors.Colors.get(4));
                mPresenter.setPostColor(JPUtils.Colors.Colors.get(4));
                break;
            case "cp_green":
                mInstance.setContainerBackgroundColor(JPUtils.Colors.Colors.get(5));
                mPresenter.setPostColor(JPUtils.Colors.Colors.get(5));
                break;
        }
        if (alertDialog != null)
            alertDialog.dismiss();
    }

}
