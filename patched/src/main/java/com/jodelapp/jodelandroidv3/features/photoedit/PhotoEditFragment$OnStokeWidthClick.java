package com.jodelapp.jodelandroidv3.features.photoedit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.jodelapp.jodelandroidv3.view.drawing.DrawingView;
import com.tellm.android.app.mod.R;

import java.lang.reflect.Field;

/**
 * ? For enhanced photo editing
 */
class PhotoEditFragment$OnStokeWidthClick implements View.OnClickListener {

    private DrawingView drawingCanvas;
    private Activity mActivity;
    private AlertDialog mAlertDialog;

    public PhotoEditFragment$OnStokeWidthClick(Activity mActivity, DrawingView drawingCanvas) {
        this.drawingCanvas = drawingCanvas;
        this.mActivity = mActivity;
    }

    @Override
    public void onClick(View v) {
        final View mDialogView = mActivity.getLayoutInflater().inflate(R.layout.dialog_thickness_picker, null);
        final NumberPicker mNumberPicker = mDialogView.findViewById(R.id.numberPicker);
        mNumberPicker.setMinValue(1);
        mNumberPicker.setMaxValue(60);
        setDividerColor(mNumberPicker, mActivity.getResources().getColor(R.color.light_orange));
        setNumberPickerTextColor(mNumberPicker, mActivity.getResources().getColor(R.color.orange));

        mDialogView.findViewById(R.id.btnStrokeWidthConfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawingCanvas.setPaintStrokeWidth(mNumberPicker.getValue());
                mAlertDialog.dismiss();
            }
        });

        mAlertDialog = new AlertDialog.Builder(mActivity)
                .setView(mDialogView)
                .create();

        mAlertDialog.show();
    }

    private void setDividerColor(NumberPicker picker, int color) {
        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {
                pf.setAccessible(true);
                try {
                    ColorDrawable colorDrawable = new ColorDrawable(color);
                    pf.set(picker, colorDrawable);
                } catch (IllegalArgumentException | Resources.NotFoundException | IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    private boolean setNumberPickerTextColor(NumberPicker numberPicker, int color) {
        final int count = numberPicker.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = numberPicker.getChildAt(i);
            if (child instanceof EditText) {
                try {
                    Field selectorWheelPaintField = numberPicker.getClass()
                            .getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
                    ((Paint) selectorWheelPaintField.get(numberPicker)).setColor(color);
                    ((EditText) child).setTextColor(color);
                    numberPicker.invalidate();
                    return true;
                } catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException e) {
                    e.printStackTrace();
                }

            }
        }
        return false;
    }
}
