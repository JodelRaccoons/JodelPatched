package com.jodelapp.jodelandroidv3.jp;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.jodelapp.jodelandroidv3.JodelApp;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Used for getting the current unspoofed location
 */

public class JPLocationManager {

    private static JPStorage mStorage = new JPStorage();

    private static LocationListener mLocList = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onProviderDisabled(String provider) {}
    };
    private static Location mLocation = null;

    public static Location getLocation() {
        if (mLocation != null) {
            Calendar calendar = new GregorianCalendar();
            calendar.setTimeInMillis(mLocation.getTime());
            if (calendar.get(Calendar.MINUTE) < 5)
                return mLocation;
        }
        int loops = 0;
        while (mLocation == null && loops <= 5) {
            try {
                LocationManager mLocationManager = (LocationManager) JodelApp.staticContext.getSystemService(LOCATION_SERVICE);

                boolean isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

                boolean isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                if (isGPSEnabled || isNetworkEnabled) {

                    if (isNetworkEnabled) {
                        mLocationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, mLocList, null);
                        mLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    }

                    if (isGPSEnabled) {
                        if (mLocation == null) {
                            mLocationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, mLocList, null);
                            mLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        }
                    }

                    if (mLocation == null) {
                        mLocationManager.requestSingleUpdate(LocationManager.PASSIVE_PROVIDER, mLocList, null);
                        mLocation = mLocationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                    }
                }

                if (mLocation != null)
                    Log.d(JPLocationManager.class.getSimpleName(), "Got location after "+loops+" iterations: "+ mLocation.toString());

                mStorage.setLastKnownRealLocation(mLocation);
            } catch (Exception e) {
                e.printStackTrace();
            }
            loops++;
        }


        if (mLocation == null) {
            Log.e(JPLocationManager.class.getSimpleName(), "Was not able to find location after 5 iterations, trying to get it from the storage");
            mLocation = mStorage.getLastKnownRealLocation();
        }
        if (mLocation == null) {
            Log.e(JPLocationManager.class.getSimpleName(), "Last known real location is null...");
        }

        return mLocation;
    }
}
