package com.jodelapp.jodelandroidv3;

import com.squareup.otto.Bus;

import lanchon.dexpatcher.annotation.DexAction;
import lanchon.dexpatcher.annotation.DexAdd;
import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexIgnore;

@DexEdit(staticConstructorAction = DexAction.ADD)
public class AppModule {

    @DexIgnore
    public AppModule() {
    }

    @DexAdd
    public static Bus staticBus;

    static {
    }

    @DexAdd
    private Bus createBus() {
        Bus bus = source_createBus();
        staticBus = bus;
        return bus;
    }

    @DexEdit(target = "createBus")
    private Bus source_createBus() {
        return null;
    }
}
