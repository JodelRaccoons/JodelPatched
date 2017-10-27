package com.jodelapp.jodelandroidv3;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.jodelapp.jodelandroidv3.jp.JPLocationManager;

import lanchon.dexpatcher.annotation.DexAdd;
import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexWrap;

@SuppressWarnings("InfiniteRecursion")
@DexEdit
public class JodelApp extends Application {

    @SuppressLint("StaticFieldLeak")
    @DexAdd
    public static Context staticContext;

    @SuppressLint("MissingSuperCall")
    @DexWrap
    public void onCreate() {
        onCreate();
        JPLocationManager.getLocation(); //Doing this first will prevent loading times when entering the settingsfragment
        staticContext = this;
    }

}
