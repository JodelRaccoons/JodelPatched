package com.jodelapp.jodelandroidv3.jp;

import android.content.Context;
import android.location.Location;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.jodelapp.jodelandroidv3.AppModule;
import com.jodelapp.jodelandroidv3.data.googleservices.location.LocationUpdatesOnSubscribe$$Lambda$1;
import com.jodelapp.jodelandroidv3.events.FeedUpdateEvent;
import com.jodelapp.jodelandroidv3.events.HomeModeOffEvent;
import com.jodelapp.jodelandroidv3.events.LocationUpdateEvent;
import com.jodelapp.jodelandroidv3.view.MainActivity;
import com.squareup.otto.Bus;
import com.tellm.android.app.mod.R;

import lanchon.dexpatcher.annotation.DexAdd;

@DexAdd
public class JPUtils {

    public static int getDiptoPx(Context context, int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
    }

    /*
    * Broken, can't find the mistake
    * */
    public static void showMessageSnackbar(String message) {
        View snackBarContainer = MainActivity.staticActivity.findViewById(R.id.snackbar_container);
        TSnackbar mSnackbar = TSnackbar.a(snackBarContainer, message, -1);
        View view = mSnackbar.getView();
        view.setBackgroundColor(MainActivity.staticActivity.getResources().getColor(R.color.white));
        TextView textView = (TextView) view.findViewById(R.id.snackbar_text);
        textView.setGravity(17);
        textView.setTextColor(MainActivity.staticActivity.getResources().getColor(R.color.darkish_gray));
        mSnackbar.show();
    }

    /*
    * Possible update sensitive method
    * */
    public static void addFragmentToContent(FragmentActivity mActivity, Fragment mFragment) {
        mActivity.getSupportFragmentManager().aP().a(android.R.id.content, mFragment).d("").commit();
    }

    public static void updateJodelLocation() {
        JPStorage mStorage = new JPStorage();
        Location mLocation = new Location("JP");
        double[] mLatLng = mStorage.getSpoofLocation();
        mLocation.setLatitude(mLatLng[0]);
        mLocation.setLongitude(mLatLng[1]);
        mLocation.setAccuracy(1.0f);
        updateJodelLocation(mLocation);
    }

    public static void updateJodelLocation(Location mLocation) {
        Bus mBus = AppModule.staticBus;
        mBus.post(new LocationUpdateEvent(mLocation));
        mBus.post(new HomeModeOffEvent());
        mBus.post(new FeedUpdateEvent());
    }

    public static Location getLocation() {
        JPStorage mStorage = new JPStorage();
        boolean spoofed = mStorage.isSpoofLocation();

        if (spoofed) {
            Location mLocation = new Location("JP");
            mLocation.setLatitude(mStorage.getSpoofLocation()[0]);
            mLocation.setLongitude(mStorage.getSpoofLocation()[1]);
            return mLocation;
        } else {
            return JPLocationManager.getLocation();
        }
    }

    public static boolean isValidLatLng(double lat, double lng){
        if(lat < -90 || lat > 90)
        {
            return false;
        }
        else if(lng < -180 || lng > 180)
        {
            return false;
        }
        return true;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static boolean isValidDouble(String mDouble) {
        try {
            Double.parseDouble(mDouble);
            Location.convert(mDouble);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
