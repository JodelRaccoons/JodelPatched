package com.jodelapp.jodelandroidv3.utilities;


import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexIgnore;

@DexEdit
public class UniqueDeviceIdentifier {

    @DexIgnore
    public UniqueDeviceIdentifier() {
    }

    /*@DexAdd
    public String getHash() {
        Log.i("Jodel", "Hijacking udi check");
        return "";
    }

    @DexEdit(target = "getHash")
    public String source_getHash() {
        return "";
    }*/

}
