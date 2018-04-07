package com.jodelapp.jodelandroidv3.jp;

import android.content.SharedPreferences;
import android.location.Address;
import android.location.Location;

import com.google.gson.Gson;
import com.jodelapp.jodelandroidv3.JodelApp;

import java.util.TreeMap;
import java.util.regex.Pattern;

import lanchon.dexpatcher.annotation.DexAdd;

/**
 * ? For JodelPatched storage
 */
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

    public void setFastLocationSpoof(int index, Address mAddress) {
        fastLocationStorage.edit().putString(String.valueOf(index), new Gson().toJson(mAddress)).apply();
    }

    public Address getFastLocationSpoof(int index){
        String storedString = fastLocationStorage.getString(String.valueOf(index),null);
        if (storedString != null) {
            try {
                return new Gson().fromJson(storedString,Address.class);
            } catch (Exception e) {
                return null;
            }
        } else return null;
    }

    public Location getLastKnownRealLocation() {
        String location = settings.getString("last_known_real_location", null);
        if (location == null)
            return null;
        try {
            return new Gson().fromJson(location, Location.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setLastKnownRealLocation(Location mLocation) {
        settings.edit().putString("last_known_real_location", new Gson().toJson(mLocation)).apply();
    }

    public double[] getSpoofLocation() {
        double[] loc = new double[2];
        loc[0] = Double.parseDouble(settings.getString(SPOOF_LOCATION_LAT, "0.0"));
        loc[1] = Double.parseDouble(settings.getString(SPOOF_LOCATION_LNG, "0.0"));
        return loc;
    }

    public boolean setSpoofLocation() {
        return settings.getBoolean(SPOOF_LOCATION, false);
    }

    public void setSpoofLocation(boolean val) {
        settings.edit().putBoolean(SPOOF_LOCATION, val).apply();
    }
}