package com.jodelapp.jodelandroidv3.data.googleservices.location;

import android.location.Location;

import com.google.android.gms.location.LocationListener;
import com.jodelapp.jodelandroidv3.AppModule;
import com.jodelapp.jodelandroidv3.events.LocationUpdateEvent;
import com.jodelapp.jodelandroidv3.jp.JPStorage;
import com.squareup.otto.Subscribe;

import lanchon.dexpatcher.annotation.DexAction;
import lanchon.dexpatcher.annotation.DexAdd;
import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexWrap;

@DexEdit(defaultAction = DexAction.ADD)
public class LocationUpdatesOnSubscribe$$Lambda$1 implements LocationListener {

    /*
    * Required to prevent doubled registration of the EventBus
    * */
    @DexAdd
    private static boolean isRegistered = false;

    @DexAdd
    @Subscribe
    public void subscribe(LocationUpdateEvent mLocationUpdateEvent) {
        mLocationUpdateEvent.location.setLatitude(mLocationUpdateEvent.location.getLatitude());
        mLocationUpdateEvent.location.setLongitude(mLocationUpdateEvent.location.getLongitude());
        onLocationChanged(mLocationUpdateEvent.location);
    }

    @DexWrap
    public void onLocationChanged(Location location) {
        if (!isRegistered){
            AppModule.staticBus.register(this);
            isRegistered = true;
        }

        JPStorage storage = new JPStorage();
        if (storage.isSpoofLocation()) {
            double[] loc = storage.getSpoofLocation();
            location.setLatitude(loc[0]);
            location.setLongitude(loc[1]);
        }
        onLocationChanged(location);
    }
}