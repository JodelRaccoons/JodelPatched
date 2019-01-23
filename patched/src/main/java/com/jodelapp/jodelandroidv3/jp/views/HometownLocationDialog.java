package com.jodelapp.jodelandroidv3.jp.views;

import android.animation.LayoutTransition;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Space;
import android.widget.TextView;

import com.jodelapp.jodelandroidv3.jp.JPStorage;
import com.jodelapp.jodelandroidv3.view.MainActivity;
import com.jodelapp.jodelandroidv3.view.MainActivity$OnAddLocationClickListener;
import com.jodelapp.jodelandroidv3.view.MainActivity$OnEmptyEntryClickListener;
import com.jodelapp.jodelandroidv3.view.MainActivity$OnEntryClickListener;
import com.jodelapp.jodelandroidv3.view.MainActivity$OnEntryLongClickListener;
import com.jodelapp.jodelandroidv3.view.MainActivity$OnHeaderClickListener;
import com.jodelapp.jodelandroidv3.view.MainActivity$OnHometownClickListener;
import com.jodelapp.jodelandroidv3.view.MainActivity$OnToggleLocationClickListener;
import com.tellm.android.app.mod.R;

import lanchon.dexpatcher.annotation.DexAdd;

import static android.view.View.GONE;
import static android.widget.LinearLayout.HORIZONTAL;
import static com.jodelapp.jodelandroidv3.jp.JPUtils.dpToPx;

public class HometownLocationDialog {

    @DexAdd
    @SuppressWarnings("ResourceType")
    public static View getAlertDialogView(AlertDialog mAlertDialog, View viewToPassToHometown, MainActivity mainActivity) {
        final JPStorage jpStorage = new JPStorage();

        LinearLayout rootLL = new LinearLayout(mainActivity);
        rootLL.setOrientation(LinearLayout.VERTICAL);

        RelativeLayout headerParent = new RelativeLayout(mainActivity);
        headerParent.setLayoutTransition(new LayoutTransition());
        headerParent.setId(123454);
        headerParent.setGravity(Gravity.CENTER);
        headerParent.setBackgroundColor(Color.parseColor("#FF9908"));


        //**********************HEADER VIEW*****************************

        final RelativeLayout headerView = new RelativeLayout(mainActivity);


        LinearLayout.LayoutParams headerLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        headerView.setPadding(0, dpToPx(7), 0, dpToPx(24));

        headerView.setLayoutParams(headerLayoutParams);

        headerView.setBackgroundColor(Color.parseColor("#FF9908"));


        //HometownLL beginn
        //mainActivity view includes the hometown redirection, the toggle location and a spacer between
        //Aditionally there is one view with a crossed line indicating the location spoofing status
        LinearLayout hometownLL = new LinearLayout(mainActivity);
        hometownLL.setOrientation(HORIZONTAL);
        RelativeLayout.LayoutParams hometownLLP = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        hometownLLP.addRule(RelativeLayout.ALIGN_PARENT_START);
        hometownLLP.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        hometownLL.setLayoutParams(hometownLLP);

        ImageView ibHometown = new ImageView(mainActivity);
        LinearLayout.LayoutParams ibLayoutParams = new LinearLayout.LayoutParams(dpToPx(40), dpToPx(40));
        ibLayoutParams.leftMargin = dpToPx(14);
        ibLayoutParams.topMargin = dpToPx(7);
        ibHometown.setOnClickListener(new MainActivity$OnHometownClickListener(mainActivity, viewToPassToHometown, mAlertDialog));
        ibHometown.setBackgroundColor(Color.TRANSPARENT);
        ibHometown.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        ibHometown.setLayoutParams(ibLayoutParams);
        ibHometown.setImageDrawable(mainActivity.getDrawable(R.drawable.ic_house));
        hometownLL.addView(ibHometown);

        Space mSpace = new Space(mainActivity);
        LinearLayout.LayoutParams mSpaceLayoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        mSpaceLayoutParams.weight = 1;
        mSpace.setLayoutParams(mSpaceLayoutParams);
        hometownLL.addView(mSpace);

        RelativeLayout rlToggleLocation = new RelativeLayout(mainActivity);
        LinearLayout.LayoutParams rlToggleLocationLayoutParams = new LinearLayout.LayoutParams(dpToPx(40), dpToPx(40));
        rlToggleLocationLayoutParams.rightMargin = dpToPx(14);
        rlToggleLocationLayoutParams.topMargin = dpToPx(7);
        rlToggleLocation.setLayoutParams(rlToggleLocationLayoutParams);

        ImageView ivCrossingLine = new ImageView(mainActivity);
        RelativeLayout.LayoutParams ivCrossingLineLayoutParams = new RelativeLayout.LayoutParams(dpToPx(35), dpToPx(35));
        ivCrossingLineLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        ivCrossingLine.setLayoutParams(ivCrossingLineLayoutParams);
        ivCrossingLine.setBackgroundColor(Color.TRANSPARENT);
        ivCrossingLine.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        ivCrossingLine.setImageDrawable(mainActivity.getDrawable(R.drawable.ic_diagonal_line));

        ImageView ibToggleLocation = new ImageView(mainActivity);
        ibToggleLocation.setOnClickListener(new MainActivity$OnToggleLocationClickListener(mainActivity, ivCrossingLine, jpStorage));
        ibToggleLocation.setBackgroundColor(Color.TRANSPARENT);
        ibToggleLocation.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        ibToggleLocation.setImageDrawable(mainActivity.getDrawable(R.drawable.ic_toggle_location));

        if (jpStorage.setSpoofLocation()) {
            ivCrossingLine.setVisibility(View.INVISIBLE);
        } else {
            ivCrossingLine.setVisibility(View.VISIBLE);
        }

        rlToggleLocation.addView(ibToggleLocation);
        rlToggleLocation.addView(ivCrossingLine);

        hometownLL.addView(rlToggleLocation);

        headerView.addView(hometownLL);

        //hometownLL end

        ImageView ivMapLocation = new ImageView(mainActivity);
        ivMapLocation.setId(123455);
        RelativeLayout.LayoutParams ivLayoutParams = new RelativeLayout.LayoutParams(dpToPx(64), dpToPx(64));
        ivLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        ivLayoutParams.setMargins(0, dpToPx(17), 0, dpToPx(5));
        ivMapLocation.setLayoutParams(ivLayoutParams);
        ivMapLocation.setImageDrawable(mainActivity.getDrawable(R.drawable.ic_map_location));


        TextView headerTextView = new TextView(mainActivity);
        RelativeLayout.LayoutParams tvLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tvLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        tvLayoutParams.addRule(RelativeLayout.BELOW, 123455);
        tvLayoutParams.setMargins(0, dpToPx(12), 0, 0);
        headerTextView.setPadding(0, dpToPx(12), 0, 0);
        headerTextView.setText("LOCATION SWITCH");
        headerTextView.setLayoutParams(tvLayoutParams);

        headerView.addView(ivMapLocation);
        headerView.addView(headerTextView);

        headerParent.addView(headerView);

        //****************HELPER VIEW****************************


        final LinearLayout helpLinearLayout = new LinearLayout(mainActivity);
        helpLinearLayout.setOrientation(LinearLayout.VERTICAL);
        helpLinearLayout.setBackgroundColor(Color.parseColor("#FF9908"));
        RelativeLayout.LayoutParams helpLinearLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        helpLinearLayoutParams.setMargins(dpToPx(18), dpToPx(18), dpToPx(18), dpToPx(18));
        helpLinearLayoutParams.addRule(RelativeLayout.BELOW, 123454);
        helpLinearLayout.setLayoutParams(helpLinearLayoutParams);


        ImageView helpImageView = new ImageView(mainActivity);
        LinearLayout.LayoutParams helpImageViewLayoutParams = new LinearLayout.LayoutParams(dpToPx(36), dpToPx(36));
        helpImageViewLayoutParams.setMargins(0, 0, 0, dpToPx(12));
        helpImageViewLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        helpImageView.setLayoutParams(helpImageViewLayoutParams);
        helpImageView.setImageDrawable(mainActivity.getDrawable(R.drawable.ic_information));


        final TextView helpDetail = new TextView(mainActivity);
        LinearLayout.LayoutParams helpLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        helpLayoutParams.setMargins(dpToPx(12), 0, dpToPx(12), dpToPx(12));
        helpDetail.setTypeface(Typeface.SANS_SERIF);
        helpDetail.setText(
                "To switch to a city, just select it by clicking it. " +
                        "If you want to change the cities in here, long press the one you want to change and select a new one in the location Picker.");

        helpLinearLayout.addView(helpImageView);
        helpLinearLayout.addView(helpDetail);

        helpLinearLayout.setVisibility(GONE);

        headerParent.addView(helpLinearLayout);

        headerParent.setOnClickListener(new MainActivity$OnHeaderClickListener(headerView, helpLinearLayout));

        rootLL.addView(headerParent);


        LinearLayout locationsLL = new LinearLayout(mainActivity);
        LinearLayout.LayoutParams locationsLL_LLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        locationsLL.setLayoutParams(locationsLL_LLP);
        locationsLL.setOrientation(LinearLayout.VERTICAL);

        int textViewHeight = dpToPx(30);

        int i;
        for (i = 1; i <= jpStorage.getNumFastSpoofLocations(); i++) {
            LinearLayout.LayoutParams subLLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            subLLP.gravity = Gravity.CENTER;
            subLLP.setMargins(0, dpToPx(12), 0, dpToPx(12));

            TextView textView = new TextView(mainActivity);
            textView.setGravity(Gravity.CENTER);

            final Address mEntry = jpStorage.getFastLocationSpoof(i);
            if (mEntry != null) {
                textView.setText(mEntry.getLocality());

                textView.setOnClickListener(new MainActivity$OnEntryClickListener(jpStorage, mEntry, mAlertDialog));
            } else {
                textView.setText("Not set");
                textView.setOnClickListener(new MainActivity$OnEmptyEntryClickListener(mainActivity));
            }

            textView.setOnLongClickListener(new MainActivity$OnEntryLongClickListener(i, mAlertDialog, jpStorage, textView));

            textView.setLayoutParams(subLLP);
            locationsLL.addView(textView);

            if (i < jpStorage.getNumFastSpoofLocations() + 1) {
                View divider = new View(mainActivity);
                LinearLayout.LayoutParams dividerLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3);
                divider.setBackgroundColor(Color.LTGRAY);
                divider.setLayoutParams(dividerLayoutParams);
                locationsLL.addView(divider);
            }

            textView.measure(0, 0);
            if (textView.getMeasuredHeight() != 0 && textViewHeight == dpToPx(15)) {
                textViewHeight = textView.getMeasuredHeight();
                Log.d(MainActivity.class.getSimpleName(), "Measured textview height is: " + textViewHeight);
            } else {
                Log.d(MainActivity.class.getSimpleName(), "Was not able to measure height: " + textViewHeight);
            }

        }
        LinearLayout.LayoutParams addLLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, textViewHeight);
        addLLP.gravity = Gravity.CENTER;
        addLLP.setMargins(0, dpToPx(12), 0, dpToPx(12));
        ImageView addImageView = new ImageView(mainActivity);
        addImageView.setTag("addImageView");
        addImageView.setImageDrawable(mainActivity.getDrawable(R.drawable.ic_add_square_button));
        addImageView.setLayoutParams(addLLP);
        addImageView.setOnClickListener(new MainActivity$OnAddLocationClickListener(locationsLL, mainActivity, jpStorage, mAlertDialog));

        locationsLL.addView(addImageView);

        ScrollView mScrollView = new ScrollView(mainActivity);
        mScrollView.setFillViewport(true);
        mScrollView.addView(locationsLL);

        rootLL.addView(mScrollView);

        return rootLL;
    }
}
