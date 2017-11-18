package com.jodelapp.jodelandroidv3;

import com.squareup.otto.Bus;

import lanchon.dexpatcher.annotation.DexAdd;
import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexWrap;

@DexEdit
public class AppModule {

    @DexAdd
    public static Bus staticBus;


    @DexWrap
    private Bus createBus() {
        Bus bus = createBus();
        staticBus = bus;
        return bus;
    }
}
