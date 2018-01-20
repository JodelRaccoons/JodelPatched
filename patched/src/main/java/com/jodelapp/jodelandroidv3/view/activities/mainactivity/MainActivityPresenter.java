package com.jodelapp.jodelandroidv3.view.activities.mainactivity;

import android.util.Log;

import com.jodelapp.jodelandroidv3.jp.ForceLocationRequestEvent;
import com.squareup.otto.Subscribe;

import lanchon.dexpatcher.annotation.DexAdd;
import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexIgnore;

/**
 * ? For instant location switching
 */
@DexEdit(contentOnly = true)
public class MainActivityPresenter {

    @DexAdd
    @Subscribe
    public void subscribe(ForceLocationRequestEvent event) {
        Log.i("JodelPatched", "Forced location request");
        requestCurrentLocation();
    }

    @DexIgnore
    private void requestCurrentLocation() {
    }
}
