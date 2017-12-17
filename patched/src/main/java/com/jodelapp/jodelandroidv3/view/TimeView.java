package com.jodelapp.jodelandroidv3.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;

import lanchon.dexpatcher.annotation.DexAction;
import lanchon.dexpatcher.annotation.DexAdd;
import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexIgnore;
import lanchon.dexpatcher.annotation.DexReplace;
import lanchon.dexpatcher.annotation.DexWrap;

/**
 * Created by Admin on 07.12.2017.
 */

@SuppressWarnings({"unused", "InfiniteRecursion", "FieldCanBeLocal"})
@DexEdit(defaultAction = DexAction.IGNORE, contentOnly = true)
public class TimeView extends TextView{

    @DexIgnore
    private long lastUpdate;
    @DexIgnore
    private DateTime dateTime;

    @DexAdd
    public final static long MILLIS_PER_DAY = 24 * 60 * 60 * 1000L;

    public TimeView(Context context) {
        super(context);
    }

    @SuppressLint("SimpleDateFormat")
    @DexWrap
    private void update() {
        if (dateTime != null){
            this.lastUpdate = System.currentTimeMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = new Date(dateTime.getMillis());
            Date date2 = new Date(System.currentTimeMillis());

            boolean moreThanDay = Math.abs(date1.getTime() - date2.getTime()) > MILLIS_PER_DAY;

            if (!moreThanDay) {
                setText(new SimpleDateFormat("HH:mm").format(new Date(dateTime.getMillis()))
                        + " | "
                        + getTimeDiff(this.dateTime));
            } else {
                setText(getTimeDiff(this.dateTime) + " | " +
                        new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date(dateTime.getMillis())));
            }
        } else update();
    }

    @DexIgnore
    private static String getTimeDiff(DateTime dateTime) {
        return null;
    }

}
