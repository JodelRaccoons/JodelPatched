package com.jodelapp.jodelandroidv3.jp;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.jodelapp.jodelandroidv3.view.MainActivity;

import hack.getid.R;
import lanchon.dexpatcher.annotation.DexAdd;

@DexAdd
public class JPUtils {
    public static int getDiptoPx(Context context, int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
    }

    /*
    * Broken, can't find the mistake
    * */
    public static void showMessageSnackbar(String message) {
        View snackBarContainer = MainActivity.staticActivity.findViewById(R.id.snackbar_container);
        TSnackbar mSnackbar = TSnackbar.a(snackBarContainer, message, -1);
        View view = mSnackbar.getView();
        view.setBackgroundColor(MainActivity.staticActivity.getResources().getColor(R.color.white));
        TextView textView = (TextView) view.findViewById(R.id.snackbar_text);
        textView.setGravity(17);
        textView.setTextColor(MainActivity.staticActivity.getResources().getColor(R.color.darkish_gray));
        mSnackbar.show();
    }
}
