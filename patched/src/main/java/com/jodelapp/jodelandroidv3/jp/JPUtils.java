package com.jodelapp.jodelandroidv3.jp;

import android.content.Context;
import android.util.TypedValue;

import lanchon.dexpatcher.annotation.DexAdd;

@DexAdd
public class JPUtils {
    public static int getDiptoPx(Context context, int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
    }
}
