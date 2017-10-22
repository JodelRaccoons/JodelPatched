package com.jodelapp.jodelandroidv3.data.googleservices.location;

import android.location.Location;
import android.util.Log;

import com.google.android.gms.location.LocationListener;
import com.jodelapp.jodelandroidv3.jp.JPStorage;
import com.jodelapp.jodelandroidv3.model.Storage;

import lanchon.dexpatcher.annotation.DexAction;
import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexWrap;

@DexEdit(defaultAction = DexAction.ADD)
public class LocationUpdatesOnSubscribe$$Lambda$1 implements LocationListener {

    @DexWrap
    public void onLocationChanged(Location location) {
        JPStorage storage = new JPStorage();
        if (storage.isSpoofLocation()) {
            double[] loc = storage.getSpoofLocation();
            location.setLatitude(loc[0]);
            location.setLongitude(loc[1]);
        }
        onLocationChanged(location);
    }
}