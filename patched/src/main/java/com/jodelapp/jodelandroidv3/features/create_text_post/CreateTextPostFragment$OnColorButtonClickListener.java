package com.jodelapp.jodelandroidv3.features.create_text_post;

import android.app.AlertDialog;
import android.view.View;

/**
 * Created by Admin on 04.01.2018.
 */

public class CreateTextPostFragment$OnColorButtonClickListener implements  View.OnClickListener {

    private CreateTextPostFragment mInstance;

    public CreateTextPostFragment$OnColorButtonClickListener(CreateTextPostFragment mInstance){
        this.mInstance = mInstance;
    }

    @Override
    public void onClick(View v) {
        View dialoglayout = mInstance.getViews().getColorPickerView();

        AlertDialog.Builder builder = new AlertDialog.Builder(mInstance.getActivity());
        builder.setTitle("Pick your desired color");
        builder.setView(dialoglayout);

        AlertDialog mAlertDialog = builder.create();

        CreateTextPostFragment$OnColorChoosenListener colorPickerOnClickListener =
                new CreateTextPostFragment$OnColorChoosenListener(mAlertDialog, mInstance);

        dialoglayout.findViewWithTag("cp_orange").setOnClickListener(colorPickerOnClickListener);
        dialoglayout.findViewWithTag("cp_yellow").setOnClickListener(colorPickerOnClickListener);
        dialoglayout.findViewWithTag("cp_red").setOnClickListener(colorPickerOnClickListener);
        dialoglayout.findViewWithTag("cp_blue").setOnClickListener(colorPickerOnClickListener);
        dialoglayout.findViewWithTag("cp_bluegrayish").setOnClickListener(colorPickerOnClickListener);
        dialoglayout.findViewWithTag("cp_green").setOnClickListener(colorPickerOnClickListener);

        mAlertDialog.show();
    }
}