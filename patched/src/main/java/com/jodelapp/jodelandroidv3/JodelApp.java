package com.jodelapp.jodelandroidv3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.multidex.MultiDexApplication;

import com.jodelapp.jodelandroidv3.jp.JPLocationManager;
import com.jodelapp.jodelandroidv3.jp.OnlyDebug;

import java.lang.reflect.Method;

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
    @DexAdd
    private static JodelApp mInstance;

    @DexAdd
    public static JodelApp get() {
        return mInstance;
    }

    @DexWrap
    private final void initDagger() {
        initDagger();
        JodelApp.mInstance = this;
        staticContext = getApplicationContext();
        new OnlyDebug();
        JPLocationManager.getLocation(); //Doing this first will prevent loading times when entering the settingsfragment
    }

    @DexIgnore
    public AppComponent getAppComponent() {
        return null;
    }
}