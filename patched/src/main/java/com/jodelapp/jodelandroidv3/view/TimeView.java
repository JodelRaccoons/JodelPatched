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
import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexIgnore;
import lanchon.dexpatcher.annotation.DexReplace;
import lanchon.dexpatcher.annotation.DexWrap;

/**
 * Created by Admin on 07.12.2017.
 */

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
    @DexReplace
    private void update() {
        this.lastUpdate = System.currentTimeMillis();
        setText(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date(dateTime.getMillis()))
                + " | "
                + getTimeDiff(this.dateTime));
    }

    @DexIgnore
    private static String getTimeDiff(DateTime dateTime) {
        return null;
    }

}
