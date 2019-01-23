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
import android.widget.ScrollView;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import com.jodelapp.jodelandroidv3.analytics.state.EntryPoint;
import com.jodelapp.jodelandroidv3.jp.JPStorage;
import com.jodelapp.jodelandroidv3.jp.views.HometownLocationDialog;
import com.tellm.android.app.mod.R;

import lanchon.dexpatcher.annotation.DexAdd;
import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexIgnore;
import lanchon.dexpatcher.annotation.DexWrap;

import static android.view.View.GONE;
import static android.widget.LinearLayout.HORIZONTAL;
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

    @SuppressWarnings("InfiniteRecursion")
    @SuppressLint("MissingSuperCall")
    @DexWrap
    protected void onCreate(Bundle bundle) {
        onCreate(bundle);
        staticActivity = this;
    }

    @DexIgnore
    @Override
    protected EntryPoint getEntryPoint() {
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

    @DexAdd
    static void lambda$setupHomeTownTab$5(MainActivity mainActivity, View view){
        if (view.getId() == R.id.feed_tab) {
            final AlertDialog dialog = new AlertDialog.Builder(mainActivity).create();
            dialog.setView(HometownLocationDialog.getAlertDialogView(dialog, view, mainActivity));
            dialog.show();
        }
    }

    @DexEdit(target = "lambda$setupHomeTownTab$5")
    static void source_lambda$setupHomeTownTab$5(MainActivity mainActivity, View view){}
}
