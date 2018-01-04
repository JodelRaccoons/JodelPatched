package com.jodelapp.jodelandroidv3.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.TextView;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Date;

import lanchon.dexpatcher.annotation.DexAction;
import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexIgnore;
import lanchon.dexpatcher.annotation.DexWrap;

/**
 * ? For adjusted time format
 */
@SuppressWarnings({"unused", "InfiniteRecursion", "FieldCanBeLocal"})
@DexEdit(defaultAction = DexAction.IGNORE, contentOnly = true)
public class TimeView extends TextView{

    @DexIgnore
    private long lastUpdate;
    @DexIgnore
    private DateTime dateTime;

    public TimeView(Context context) {
        super(context);
    }

    @SuppressLint("SimpleDateFormat")
    @DexWrap
    private void update() {
        if (dateTime != null){
            this.lastUpdate = System.currentTimeMillis();
            String dateFormat;
            if(lastUpdate - dateTime.getMillis() < (1000 * 3600))
                dateFormat = getTimeDiff(this.dateTime);
            else if(lastUpdate - dateTime.getMillis() < (1000 * 3600 * 24))
                dateFormat = getTimeDiff(this.dateTime)
                        + " | "
                        + new SimpleDateFormat("HH:mm").format(new Date(dateTime.getMillis()));
            else
                dateFormat = getTimeDiff(this.dateTime)
                        + " | "
                        + new SimpleDateFormat("MM/dd/yyyy HH:mm").format(new Date(dateTime.getMillis()));
            setText(dateFormat);
        } else update();
    }

    @DexIgnore
    private static String getTimeDiff(DateTime dateTime) {
        return null;
    }

}
