package com.jodelapp.jodelandroidv3.view;

import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.jodelapp.jodelandroidv3.api.model.Location;
import com.jodelapp.jodelandroidv3.jp.JPStorage;
import com.jodelapp.jodelandroidv3.jp.JPUtils;
import com.jodelapp.jodelandroidv3.jp.TSnackbar;
import com.tellm.android.app.mod.R;

import java.util.AbstractMap;
import java.util.Map;

import lanchon.dexpatcher.annotation.DexAdd;
import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexIgnore;
import lanchon.dexpatcher.annotation.DexReplace;
import lanchon.dexpatcher.annotation.DexWrap;

import static android.view.View.GONE;
import static com.jodelapp.jodelandroidv3.jp.JPUtils.dpToPx;

/*
* Stuff done so far: Removed long click listener from the hometown button. Moved the Listener into MyMenuViewHolderPresenter.
* Using this class for the staticActivity and the callback from the PlacePicker.
* */

@DexEdit(contentOnly = true)
public class MainActivity extends JodelActivity {

    @SuppressLint("StaticFieldLeak")
    @DexAdd
    public static MainActivity staticActivity;

    @SuppressLint("MissingSuperCall")
    @DexWrap
    protected void onCreate(Bundle bundle) {
        onCreate(bundle);
        staticActivity = this;
    }

    @DexIgnore
    @Override
    protected String getBottomFragmentScreenName() {
        return null;
    }

    /*
        * OnActivityResult from the LocationPicker, implemented in
        * com.jodelapp.jodelandroidv3.features.mymenu.adapter.MyMenuViewHolderPresenter#onItemClicked(MyMenuItem mMyMenuItem)
        * */
    @DexWrap
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == 108) {
            JPStorage storage = new JPStorage();
            if (data.getBooleanExtra("fast_location_picker", false)){
                int index = data.getIntExtra("fast_location_picker_slot",5);
                Address mPassedAddress = data.getParcelableExtra("address");

                if (mPassedAddress != null) {
                    storage.setFastLocationSpoof(index, mPassedAddress);
                    Toast.makeText(MainActivity.this, "Saved new location!", Toast.LENGTH_LONG).show();
                }
            } else {
                double[] latlng = data.getDoubleArrayExtra("latlng");
                Log.i("JodelPatched", "I got location! woho!");
                storage.setSpoofLocation(latlng[0], latlng[1]);
            }
        } else
            onActivityResult(requestCode, resultCode, data);
    }


    @DexReplace
    private void lambda$setupHomeTownTab$5(View view){
        if (view.getId() == R.id.feed_tab) {
            final AlertDialog dialog = new AlertDialog.Builder(this).create();
            dialog.setView(getAlertDialogView(dialog));
            dialog.show();
        }
    }


    @DexAdd
    @SuppressWarnings("ResourceType")
    private View getAlertDialogView(AlertDialog mAlertDialog) {
        LinearLayout rootLL = new LinearLayout(this);
        rootLL.setOrientation(LinearLayout.VERTICAL);

        RelativeLayout headerParent = new RelativeLayout(this);
        headerParent.setLayoutTransition(new LayoutTransition());
        headerParent.setId(123454);
        headerParent.setGravity(Gravity.CENTER);
        headerParent.setBackgroundColor(Color.parseColor("#FF9908"));


        //**********************HEADER VIEW*****************************

        final RelativeLayout headerView = new RelativeLayout(this);


        LinearLayout.LayoutParams headerLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        headerView.setPadding(0, dpToPx(24), 0, dpToPx(24));

        headerView.setLayoutParams(headerLayoutParams);

        headerView.setBackgroundColor(Color.parseColor("#FF9908"));


        ImageView imageView = new ImageView(this);
        imageView.setId(123455);
        RelativeLayout.LayoutParams ivLayoutParams = new RelativeLayout.LayoutParams(dpToPx(64), dpToPx(64));
        ivLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        imageView.setLayoutParams(ivLayoutParams);
        imageView.setImageDrawable(this.getResources().getDrawable(R.drawable.ic_map_location));

        TextView headerTextView = new TextView(this);
        RelativeLayout.LayoutParams tvLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tvLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        tvLayoutParams.addRule(RelativeLayout.BELOW, 123455);
        tvLayoutParams.setMargins(0, dpToPx(12), 0, 0);
        headerTextView.setPadding(0, dpToPx(12), 0, 0);
        headerTextView.setText("LOCATION SWITCH");
        headerTextView.setLayoutParams(tvLayoutParams);

        headerView.addView(imageView);
        headerView.addView(headerTextView);

        headerParent.addView(headerView);

        //****************HELPER VIEW****************************


        final LinearLayout helpLinearLayout = new LinearLayout(this);
        helpLinearLayout.setOrientation(LinearLayout.VERTICAL);
        helpLinearLayout.setBackgroundColor(Color.parseColor("#FF9908"));
        RelativeLayout.LayoutParams helpLinearLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        helpLinearLayoutParams.setMargins(dpToPx(18), dpToPx(18), dpToPx(18), dpToPx(18));
        helpLinearLayoutParams.addRule(RelativeLayout.BELOW, 123454);
        helpLinearLayout.setLayoutParams(helpLinearLayoutParams);


        ImageView helpImageView = new ImageView(this);
        LinearLayout.LayoutParams helpImageViewLayoutParams = new LinearLayout.LayoutParams(dpToPx(36), dpToPx(36));
        helpImageViewLayoutParams.setMargins(0, 0, 0, dpToPx(12));
        helpImageViewLayoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        helpImageView.setLayoutParams(helpImageViewLayoutParams);
        helpImageView.setImageDrawable(this.getResources().getDrawable(R.drawable.ic_information));


        final TextView helpDetail = new TextView(this);
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

        final JPStorage mStorage = new JPStorage();
        for (int i = 1; i <= 4; i++) {
            LinearLayout.LayoutParams subLLP = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            subLLP.gravity = Gravity.CENTER;
            subLLP.setMargins(0, dpToPx(12), 0, dpToPx(12));

            TextView textView = new TextView(this);
            textView.setGravity(Gravity.CENTER);

            final Address mEntry = mStorage.getFastLocationSpoof(i);
            if (mEntry != null) {
                textView.setText(mEntry.getLocality());

                textView.setOnClickListener(new MainActivity$OnEntryClickListener(mStorage, mEntry, mAlertDialog));
            } else {
                textView.setText("Not set");
                textView.setOnClickListener(new MainActivity$OnEmptyEntryClickListener(this));
            }

            textView.setOnLongClickListener(new MainActivity$OnEntryLongClickListener(i, mAlertDialog));

            textView.setLayoutParams(subLLP);
            rootLL.addView(textView);

            if (i != 4) {
                View divider = new View(this);
                LinearLayout.LayoutParams dividerLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3);
                divider.setBackgroundColor(Color.LTGRAY);
                divider.setLayoutParams(dividerLayoutParams);
                rootLL.addView(divider);
            }
        }
        return rootLL;
    }
}
