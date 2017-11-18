package com.jodelapp.jodelandroidv3.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.jodelapp.jodelandroidv3.jp.JPStorage;

import lanchon.dexpatcher.annotation.DexAdd;
import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexWrap;

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

    /*
    * OnActivityResult from the LocationPicker, implemented in
    * com.jodelapp.jodelandroidv3.features.mymenu.adapter.MyMenuViewHolderPresenter#onItemClicked(MyMenuItem mMyMenuItem)
    * */
    @DexWrap
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == 108) {
            JPStorage storage = new JPStorage();
//            storage.isSpoofLocation(true);
            double[] latlng = data.getDoubleArrayExtra("latlng");
            Log.i("JodelPatched", "I got location! woho!");
            storage.setSpoofLocation(latlng[0], latlng[1]);
        } else
            onActivityResult(requestCode, resultCode, data);
    }
}
