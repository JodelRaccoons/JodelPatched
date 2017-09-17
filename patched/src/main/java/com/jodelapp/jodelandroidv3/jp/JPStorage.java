package com.jodelapp.jodelandroidv3.jp;

import android.content.SharedPreferences;
import android.util.Log;

import com.jodelapp.jodelandroidv3.JodelApp;

import java.util.TreeMap;
import java.util.regex.Pattern;

import lanchon.dexpatcher.annotation.DexAdd;

@DexAdd
public class JPStorage {
    private static final String PREFS_NAME = "ImSorryJodel";
    private static final String FEATURES = "features";
    private static final int VERSION = 1;

    private SharedPreferences settings = null;
    private Pattern splitSpace = Pattern.compile(" ");
    private Pattern splitColon = Pattern.compile(":");

    public JPStorage() {
        settings = JodelApp.staticContext.getSharedPreferences(PREFS_NAME, 0);
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
}