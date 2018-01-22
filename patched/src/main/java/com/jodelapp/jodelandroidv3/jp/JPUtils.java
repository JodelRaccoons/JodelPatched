package com.jodelapp.jodelandroidv3.jp;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.location.Location;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.widget.EditText;

import com.jodelapp.jodelandroidv3.AppModule;
import com.jodelapp.jodelandroidv3.JodelApp;
import com.jodelapp.jodelandroidv3.events.FeedUpdateEvent;
import com.jodelapp.jodelandroidv3.events.HomeModeOffEvent;
import com.jodelapp.jodelandroidv3.events.LocationUpdateEvent;
import com.squareup.otto.Bus;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import lanchon.dexpatcher.annotation.DexAdd;

@SuppressWarnings({"ResultOfMethodCallIgnored", "WeakerAccess", "unused"})
@DexAdd
public class JPUtils {

    public static int getDiptoPx(Context context, int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
    }

    /*
    * Possible update sensitive method
    * */
    public static void addFragmentToContent(FragmentActivity mActivity, Fragment mFragment) {
        // TODO: add dynamic get for methods
        mActivity.getSupportFragmentManager().ck().a(android.R.id.content, mFragment).f("").commit();
    }

    public static void updateJodelLocation() {
        updateJodelLocation(getLocation());
    }

    private static void updateJodelLocation(Location mLocation) {
        Bus mBus = AppModule.staticBus;
        JPStorage mStorage = new JPStorage();
        mStorage.setSpoofLocation(mLocation.getLatitude(), mLocation.getLongitude());
        mBus.post(new ForceLocationRequestEvent());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AppModule.staticBus.post(new FeedUpdateEvent());
            }
        }, 100);
    }

    public static Location getLocation() {
        JPStorage mStorage = new JPStorage();
        boolean spoofed = mStorage.isSpoofLocation();

        if (spoofed) {
            Location mLocation = new Location("JP");
            if (mStorage.getSpoofLocation()[0] == 0 && mStorage.getSpoofLocation()[1] == 0)
                return JPLocationManager.getLocation();
            mLocation.setLatitude(mStorage.getSpoofLocation()[0]);
            mLocation.setLongitude(mStorage.getSpoofLocation()[1]);
            return mLocation;
        } else {
            Location mLocation = JPLocationManager.getLocation();
            if (mLocation == null){
                mLocation = new Location("JP");
                mLocation.setLatitude(0);
                mLocation.setLongitude(0);
            }
            return mLocation;
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

    public static boolean isValidDouble(String mDouble) {
        try {
            Double.parseDouble(mDouble);
            Location.convert(mDouble);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static Map<String,String> getHashMapResource(Context c, int hashMapResId) {
        Map<String,String> map = null;
        XmlResourceParser parser = c.getResources().getXml(hashMapResId);

        String key = null, value = null;

        try {
            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_DOCUMENT) {
                    Log.d("utils","Start document");
                } else if (eventType == XmlPullParser.START_TAG) {
                    if (parser.getName().equals("map")) {
                        boolean isLinked = parser.getAttributeBooleanValue(null, "linked", false);

                        map = isLinked ? new LinkedHashMap<String, String>() : new HashMap<String, String>();
                    } else if (parser.getName().equals("entry")) {
                        key = parser.getAttributeValue(null, "key");

                        if (null == key) {
                            parser.close();
                            return null;
                        }
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    if (parser.getName().equals("entry") && map != null) {
                        map.put(key, value);
                        key = null;
                        value = null;
                    }
                } else if (eventType == XmlPullParser.TEXT) {
                    if (null != key) {
                        value = parser.getText();
                    }
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return map;
    }

    public static int dpToPx(int dp) {
        DisplayMetrics displayMetrics = JodelApp.staticContext.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static class Colors {
        public static ArrayList<String> Colors = new ArrayList<String>() {{
            add("#FFFF9908"); //Orange
            add("#FFFFBA00"); //Yellow
            add("#FFDD5F5F"); //Red
            add("#FF06A3CB"); //Blue
            add("#FF8ABDB0"); //Bluegrayish
            add("#FF9EC41C"); //Green
        }};
    }

    public static void enableLongClick(EditText editText) {
        editText.setLongClickable(true);
    }
}
