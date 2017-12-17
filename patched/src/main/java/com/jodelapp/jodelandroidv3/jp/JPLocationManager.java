package com.jodelapp.jodelandroidv3.jp;

import android.location.Address;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.jodelapp.jodelandroidv3.AppComponent;
import com.jodelapp.jodelandroidv3.api.model.LocationUpdateResponse;
import com.jodelapp.jodelandroidv3.api.model.SendLocationRequest;
import com.jodelapp.jodelandroidv3.model.Storage;
import com.jodelapp.jodelandroidv3.usecases.location.BackupAppLocationRemotely;
import com.jodelapp.jodelandroidv3.usecases.location.BackupAppLocationRemotelyImpl;
import com.jodelapp.jodelandroidv3.JodelApp;
import com.jodelapp.jodelandroidv3.analytics.AnalyticsController;
import com.jodelapp.jodelandroidv3.api.JodelApi;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Used for getting the current unspoofed location
 */

public class JPLocationManager {

    @Inject
    JodelApi mJodelApi;

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
    private static Location mLocation = new Location("Default");

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

            } catch (Exception e) {
                e.printStackTrace();
            }
            loops++;
        }


        return mLocation;
    }

    public static void updateLocation(Address mAddress){
//        Address mAddress = new Address(Locale.GERMANY);
//        mAddress.setLocality("Berlin");
//        mAddress.setCountryCode("DE");
//        mAddress.setLatitude(52.520009);
//        mAddress.setLongitude(13.405049);

        JodelApp mApp = JodelApp.get();
        AppComponent mAppConponent = mApp.getAppComponent();
        Storage mStorage = mAppConponent.getStorage();

        com.jodelapp.jodelandroidv3.usecases.LocationManager mLocationManager = mAppConponent.exposeLocationManager();
        try {
            Field mAddressField = mLocationManager.getClass().getDeclaredField("address");
            mAddressField.setAccessible(true);
            mAddressField.set(mLocationManager,mAddress);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        mStorage.setNewLocationRegistered(false);

        mLocationManager.backupLocationRemotely();


    }
}
