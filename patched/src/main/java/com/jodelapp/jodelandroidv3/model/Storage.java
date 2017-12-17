package com.jodelapp.jodelandroidv3.model;

import android.util.Log;

import com.jodelapp.jodelandroidv3.jp.JPStorage;

import java.util.Arrays;
import java.util.List;

import lanchon.dexpatcher.annotation.DexAdd;
import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexIgnore;
import lanchon.dexpatcher.annotation.DexWrap;

@DexEdit
public class Storage {

    @DexAdd
    JPStorage jpStorage;

    @DexWrap
    public boolean isFeature(String feature) {
        if(feature == null || feature.isEmpty()) return false;

        List<String> features = Arrays.asList(
                "text_search"
                , "channels"
                , "international_feed"
                , "location_tags"
                //,"karma_less_prominent"
                //,"jodel_newsfeed"
                , "hashtags"
                , "new_post_ux"
                //,"pin_main_feed"
                //,"flag_reason_change"
                , "mention_prompt"
                //,"highlight_pinning"
                //,"bookmark" // scroll down + unread?
                // bookmark might cause a rx onError NPE
                , "inapp_notis"
                , "picture_geo_filter"
                , "picture_draw"
                , "mentioning_repliers"
                , "create_channel"
        );

        boolean isDefaultActive = isFeature(feature);

        if(isDefaultActive) return true;

        if(jpStorage == null) jpStorage = new JPStorage();

        jpStorage.registerFeature(feature);

        Log.i("Jodel", "default disabled feature: " + String.valueOf(feature));

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
