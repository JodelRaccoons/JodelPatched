package com.jodelapp.jodelandroidv3.jp;

import com.facebook.stetho.Stetho;

import static com.jodelapp.jodelandroidv3.JodelApp.staticContext;

public class OnlyDebug {
    public OnlyDebug() {
        Stetho.initializeWithDefaults(staticContext);
    }
}
