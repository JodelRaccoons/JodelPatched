package com.jodelapp.jodelandroidv3.utilities;


import android.graphics.Bitmap;

import lanchon.dexpatcher.annotation.DexAdd;
import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexIgnore;

@DexEdit
public class JodelImageHelper {

    @DexIgnore
    public JodelImageHelper() {
    }

    @DexAdd
    private Bitmap blur(Bitmap bitmap) {
        //source_blur(bitmap);
        return bitmap;
    }

    @DexEdit(target = "blur")
    private Bitmap source_blur(Bitmap bitmap) {
        return bitmap;
    }
}