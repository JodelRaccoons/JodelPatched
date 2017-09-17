package com.jodelapp.jodelandroidv3.utilities;


import android.graphics.Bitmap;

import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexReplace;

@DexEdit
public class JodelImageHelper {

    @DexReplace
    private Bitmap blur(Bitmap bitmap) {
        return bitmap;
    }

}