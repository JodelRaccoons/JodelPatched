package com.jodelapp.jodelandroidv3.features.create_text_post;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;

import com.jodelapp.jodelandroidv3.JodelApp;
import com.jodelapp.jodelandroidv3.jp.JPUtils;
import com.tellm.android.app.mod.R;

import lanchon.dexpatcher.annotation.DexAdd;

import static android.widget.LinearLayout.HORIZONTAL;
import static android.widget.LinearLayout.VERTICAL;
import static java.lang.Boolean.TRUE;

/**
 * Created by Admin on 04.01.2018.
 */

public class CreateTextPostFragment$Views {

    @DexAdd
    public View getColorPickerView() {
        Context ctx = JodelApp.staticContext;

        LinearLayout.LayoutParams colorLayoutParams = new LinearLayout.LayoutParams(JPUtils.dpToPx(70), JPUtils.dpToPx(70));
        colorLayoutParams.setMargins(JPUtils.dpToPx(20), JPUtils.dpToPx(20), JPUtils.dpToPx(20), JPUtils.dpToPx(20));

        LinearLayout rootLayout = new LinearLayout(ctx);
        rootLayout.setOrientation(VERTICAL);
        LinearLayout.LayoutParams rootLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rootLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        rootLayout.setLayoutParams(rootLayoutParams);

        LinearLayout firstRow = new LinearLayout(ctx);
        firstRow.setOrientation(HORIZONTAL);
        LinearLayout.LayoutParams firstRowLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        firstRowLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        firstRow.setLayoutParams(firstRowLayoutParams);

        ImageView orange = new ImageView(ctx);
        orange.setTag("cp_orange");
        orange.setBackgroundColor(Color.parseColor("#FFFF9908"));
        orange.setLayoutParams(colorLayoutParams);
        firstRow.addView(orange);

        ImageView yellow = new ImageView(ctx);
        yellow.setTag("cp_yellow");
        yellow.setBackgroundColor(Color.parseColor("#FFFFBA00"));
        yellow.setLayoutParams(colorLayoutParams);
        firstRow.addView(yellow);

        ImageView red = new ImageView(ctx);
        red.setTag("cp_red");
        red.setBackgroundColor(Color.parseColor("#FFDD5F5F"));
        red.setLayoutParams(colorLayoutParams);
        firstRow.addView(red);
        rootLayout.addView(firstRow);

        LinearLayout secondRow = new LinearLayout(ctx);
        secondRow.setOrientation(HORIZONTAL);
        LinearLayout.LayoutParams secondRowLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        secondRowLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        secondRow.setLayoutParams(secondRowLayoutParams);

        ImageView blue = new ImageView(ctx);
        blue.setTag("cp_blue");
        blue.setBackgroundColor(Color.parseColor("#FF06A3CB"));
        blue.setLayoutParams(colorLayoutParams);
        secondRow.addView(blue);

        ImageView bluegrayish = new ImageView(ctx);
        bluegrayish.setTag("cp_bluegrayish");
        bluegrayish.setBackgroundColor(Color.parseColor("#FF8ABDB0"));
        bluegrayish.setLayoutParams(colorLayoutParams);
        secondRow.addView(bluegrayish);

        ImageView green = new ImageView(ctx);
        green.setTag("cp_green");
        green.setBackgroundColor(Color.parseColor("#FF9EC41C"));
        green.setLayoutParams(colorLayoutParams);
        secondRow.addView(green);
        rootLayout.addView(secondRow);

        return rootLayout;
    }

    @DexAdd
    public void initiateViews(View mRootView){

        LinearLayout mRootRL = (LinearLayout) mRootView.findViewById(R.id.cameraButton).getParent();
        ImageView cameraView = mRootView.findViewById(R.id.cameraButton);

        mRootRL.removeView(cameraView);

        LinearLayout mSubLinearLayout = new LinearLayout(mRootView.getContext());
        mSubLinearLayout.setOrientation(HORIZONTAL);
        LinearLayout.LayoutParams mSubLayoutParams =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mSubLinearLayout.setLayoutParams(mSubLayoutParams);

        ImageView colorPickerButton = new ImageView(mRootView.getContext());
        colorPickerButton.setClickable(TRUE);
        colorPickerButton.setTag("colorPickerButton");
        colorPickerButton.setImageResource(R.drawable.ic_color_palette);
        colorPickerButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
        LinearLayout.LayoutParams mColorPickerButtonParams = new LinearLayout.LayoutParams(JPUtils.dpToPx(60), JPUtils.dpToPx(60));
        colorPickerButton.setLayoutParams(mColorPickerButtonParams);

        ImageView galleryPickerButton = new ImageView(mRootView.getContext());
        galleryPickerButton.setClickable(TRUE);
        galleryPickerButton.setTag("galleryPickerButton");
        galleryPickerButton.setImageResource(R.drawable.ic_icon_gallery);
        galleryPickerButton.setScaleType(ImageView.ScaleType.FIT_CENTER);
        LinearLayout.LayoutParams galleryPickerButtonParams = new LinearLayout.LayoutParams(JPUtils.dpToPx(60), JPUtils.dpToPx(60));
        galleryPickerButton.setLayoutParams(galleryPickerButtonParams);

        Space mSpace1 = new Space(mRootView.getContext());
        Space mSpace2 = new Space(mRootView.getContext());

        LinearLayout.LayoutParams mSpaceLayoutParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
        mSpaceLayoutParams.weight = 1;

        mSpace1.setLayoutParams(mSpaceLayoutParams);
        mSpace2.setLayoutParams(mSpaceLayoutParams);

        mSubLinearLayout.addView(mSpace1);
        mSubLinearLayout.addView(colorPickerButton);
        mSubLinearLayout.addView(cameraView);
        mSubLinearLayout.addView(galleryPickerButton);
        mSubLinearLayout.addView(mSpace2);

        mRootRL.addView(mSubLinearLayout, 1);
    }
}
