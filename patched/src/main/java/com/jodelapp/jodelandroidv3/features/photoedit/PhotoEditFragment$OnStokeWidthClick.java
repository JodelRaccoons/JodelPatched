package com.jodelapp.jodelandroidv3.features.photoedit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.NumberPicker;

import com.jodelapp.jodelandroidv3.view.drawing.DrawingView;
import com.tellm.android.app.mod.R;

/**
 * ? For enhanced photo editing
 */
class PhotoEditFragment$OnStokeWidthClick implements View.OnClickListener {

    private DrawingView drawingCanvas;
    private Activity mActivity;
    private static int backupValue = 5;

    public PhotoEditFragment$OnStokeWidthClick(Activity mActivity, DrawingView drawingCanvas) {
        this.drawingCanvas = drawingCanvas;
        this.mActivity = mActivity;
    }

    @Override
    public void onClick(View v) {
        final View mDialogView = mActivity.getLayoutInflater().inflate(R.layout.dialog_thickness_picker, null);
        final NumberPicker.Formatter mFormatter = new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return String.valueOf(value * 5);
            }
        };

        final NumberPicker mNumberPicker = (NumberPicker) mDialogView.findViewById(R.id.numberPickerStrokeWidth);
        mNumberPicker.setMinValue(1);
        mNumberPicker.setMaxValue(15);
        mNumberPicker.setValue(backupValue);
        mNumberPicker.setFormatter(mFormatter);

        AlertDialog mDialog = new AlertDialog.Builder(mActivity)
                .setView(mDialogView)
                .setTitle("Choose a stroke width")
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int value = mNumberPicker.getValue();
                        drawingCanvas.setPaintStrokeWidth(value * 5);
                        backupValue = value;
                        dialog.dismiss();
                    }
                }).create();

        mDialog.show();
    }
}
