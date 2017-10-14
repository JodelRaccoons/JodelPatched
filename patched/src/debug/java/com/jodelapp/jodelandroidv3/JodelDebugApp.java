package com.jodelapp.jodelandroidv3;

//import com.facebook.stetho.Stetho;

import lanchon.dexpatcher.annotation.DexAdd;

@DexAdd
public class JodelDebugApp extends JodelApp {

    @Override
    public void onCreate() {
        super.onCreate();
        //Stetho.initializeWithDefaults(this);
    }
}
