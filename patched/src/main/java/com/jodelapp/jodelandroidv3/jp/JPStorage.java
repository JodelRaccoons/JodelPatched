package com.jodelapp.jodelandroidv3.jp;

import android.content.SharedPreferences;
import android.location.Address;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.jodelapp.jodelandroidv3.JodelApp;
import com.jodelapp.jodelandroidv3.api.model.Location;

import java.util.AbstractMap;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

import lanchon.dexpatcher.annotation.DexAdd;

@DexAdd
public class JPStorage {
    private static final String PREFS_NAME = "ImSorryJodel";
    private static final String PREFS_FAST_LOCATION_NAME = "FastLocationPicker";
    private static final String FEATURES = "features";
    private static final String SPOOF_LOCATION = "spoof_location";
    private static final String SPOOF_LOCATION_LAT = "spoof_location_lat";
    private static final String SPOOF_LOCATION_LNG = "spoof_location_lng";
    private static final int VERSION = 1;

    private SharedPreferences settings = null;
    private SharedPreferences fastLocationStorage = null;
    private Pattern splitSpace = Pattern.compile(" ");
    private Pattern splitColon = Pattern.compile(":");

    public JPStorage() {
        settings = JodelApp.staticContext.getSharedPreferences(PREFS_NAME, 0);
        fastLocationStorage = JodelApp.staticContext.getSharedPreferences(PREFS_FAST_LOCATION_NAME, 0);
        int version = settings.getInt("version", 0);
        /*if (version < VERSION) {
            // TODO: Just reset all to 0/false
            settings.edit().remove(FEATURES).apply();
        }*/
    }

    private String getFeaturesAsString() {
        return settings.getString(FEATURES, "").trim();
    }

    public TreeMap<String, Boolean> getFeatures() {
        String raw = getFeaturesAsString();
        TreeMap<String, Boolean> map = new TreeMap<String, Boolean>();
        if (raw.isEmpty()) return map;

        for (String featureAndStatus : splitSpace.split(raw)) {
            String[] parts = splitColon.split(featureAndStatus);
            map.put(parts[0], parts[1].equals("1"));
        }
        Log.i("JodelSettings", map.toString());
        return map;
    }

    public boolean isActive(String feature) {
        return !(feature == null || feature.isEmpty()) && getFeaturesAsString().contains(feature + ":1");
    }

    public void registerFeature(String feature) {
        TreeMap<String, Boolean> features = getFeatures();
        if (!features.containsKey(feature)) {
            features.put(feature, false);
            setFeatures(features);
        }
    }

    public void setFeatures(TreeMap<String, Boolean> features) {
        StringBuilder featuresString = new StringBuilder();
        for (TreeMap.Entry<String, Boolean> keyValue : features.entrySet()) {
            featuresString
                    .append(keyValue.getKey())
                    .append(":")
                    .append(keyValue.getValue() ? "1" : "0")
                    .append(" ");
        }
        settings.edit().putString(FEATURES, featuresString.toString()).apply();
    }

    public void setSpoofLocation(double latitude, double longitude) {
        settings.edit().putString(SPOOF_LOCATION_LAT, String.valueOf(latitude)).apply();
        settings.edit().putString(SPOOF_LOCATION_LNG, String.valueOf(longitude)).apply();
    }

    public void setFastLocationSpoof(int index, Map.Entry<String,LatLng> mEntry) {
        fastLocationStorage.edit().putString(String.valueOf(index),mEntry.getKey()
                + ":" + mEntry.getValue().latitude
                + ":" + mEntry.getValue().longitude).apply();
    }

    public Map.Entry<String,LatLng> getFastLocationSpoof(int index){
        String storedString = fastLocationStorage.getString(String.valueOf(index),null);
        if (storedString != null) {
            String[] mStringArr = storedString.split(":");
            return new AbstractMap.SimpleEntry<String, LatLng>(mStringArr[0],
                    new LatLng(Double.parseDouble(mStringArr[1]), Double.parseDouble(mStringArr[2])));
        } else return null;
    }

    public double[] getSpoofLocation() {
        double[] loc = new double[2];
        loc[0] = Double.parseDouble(settings.getString(SPOOF_LOCATION_LAT, "0.0"));
        loc[1] = Double.parseDouble(settings.getString(SPOOF_LOCATION_LNG, "0.0"));
        return loc;
    }

    public boolean isSpoofLocation() {
        return settings.getBoolean(SPOOF_LOCATION, false);
    }
    public void isSpoofLocation(boolean val) {
        settings.edit().putBoolean(SPOOF_LOCATION, val).apply();
    }
}