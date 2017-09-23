package com.jodelapp.jodelandroidv3;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import lanchon.dexpatcher.annotation.DexAdd;
import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexWrap;

@DexEdit
public class JodelApp extends Application {

    @DexAdd
    public static Context staticContext;

    @SuppressLint("MissingSuperCall")
    @DexWrap
    public void onCreate() {
        onCreate();
        staticContext = this;
    }

}
