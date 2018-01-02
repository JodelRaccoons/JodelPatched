package com.jodelapp.jodelandroidv3.model;

import android.util.Log;

import com.jodelapp.jodelandroidv3.jp.JPStorage;

import java.util.Arrays;
import java.util.List;

import lanchon.dexpatcher.annotation.DexAdd;
import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexIgnore;
import lanchon.dexpatcher.annotation.DexWrap;

/**
 * ? For beta features
 */
@DexEdit
public class Storage {

    @DexAdd
    JPStorage jpStorage;

    @DexWrap
    public boolean isFeature(String feature) {
        if(feature == null || feature.isEmpty()) return false;

        boolean isDefaultActive = isFeature(feature);

        if(isDefaultActive) return true;

        if(jpStorage == null) jpStorage = new JPStorage();

        jpStorage.registerFeature(feature);

        return jpStorage.isActive(feature);
    }

    @DexWrap
    public String getUniqueDeviceIdentifier() {
        String a = getUniqueDeviceIdentifier();
        Log.i("JodelPatched:", "getUniqueDeviceIdentifier=" + a);
        return a;
    }

    @DexIgnore
    public void setUniqueDeviceIdentifier(String a) {

    }

    @DexIgnore
    public void setNewLocationRegistered(boolean z) {
    }

}
