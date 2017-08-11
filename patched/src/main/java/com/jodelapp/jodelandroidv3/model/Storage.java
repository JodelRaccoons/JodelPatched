package com.jodelapp.jodelandroidv3.model;

import java.util.Arrays;
import java.util.List;

import lanchon.dexpatcher.annotation.DexAdd;
import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexIgnore;

@DexEdit
public class Storage {

    @DexIgnore
    public Storage() {
    }

    @DexAdd
    public boolean isFeature(String feature) {
        if (feature == null || feature.isEmpty())
            return false;

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
        boolean isActive = source_isFeature(feature);
        return isActive || features.contains(feature);
    }

    @DexEdit(target = "isFeature")
    public boolean source_isFeature(String s) {
        return false;
    }
}
