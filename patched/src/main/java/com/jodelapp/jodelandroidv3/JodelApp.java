package com.jodelapp.jodelandroidv3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.jodelapp.jodelandroidv3.jp.JPLocationManager;
import com.jodelapp.jodelandroidv3.jp.OnlyDebug;

import lanchon.dexpatcher.annotation.DexAdd;
import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexIgnore;
import lanchon.dexpatcher.annotation.DexWrap;

@SuppressWarnings("InfiniteRecursion")
@DexEdit(contentOnly = true)
public class JodelApp extends MultiDexApplication {

    @SuppressLint("StaticFieldLeak")
    @DexAdd
    public static Context staticContext;

    @DexWrap
    private final void initDagger() {
        initDagger();
        staticContext = getApplicationContext();
//        staticContext = getAppComponent().getContext();
        new OnlyDebug();
        JPLocationManager.getLocation(); //Doing this first will prevent loading times when entering the settingsfragment
    }

    @DexIgnore
    public AppComponent getAppComponent() {
        return null;
    }
}