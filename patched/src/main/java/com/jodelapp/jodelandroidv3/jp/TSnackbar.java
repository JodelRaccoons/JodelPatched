package com.jodelapp.jodelandroidv3.jp;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jodelapp.jodelandroidv3.view.MainActivity;

/**
 * Created by Admin on 01.11.2017.
 */

public class TSnackbar {
    public static final int LENGTH_INDEFINITE = -2;
    public static final int LENGTH_SHORT = -1;
    public static final int LENGTH_LONG = 0;

    public static void make(String message) {
        make(message, LENGTH_LONG);
    }

    public static void make(String message, int length) {
        make(MainActivity.staticActivity, message, length);
    }

    public static void make(Activity mActivity, String message, int length) {
            try {
                ViewGroup contentView = (ViewGroup) mActivity.findViewById(android.R.id.content);
                com.androidadvance.topsnackbar.TSnackbar snackbar = null;
                switch (length) {
                    case 0:
                        snackbar = com.androidadvance.topsnackbar.TSnackbar.a(contentView, message, LENGTH_LONG);
                        break;
                    case -1:
                        snackbar = com.androidadvance.topsnackbar.TSnackbar.a(contentView, message, LENGTH_SHORT);
                        break;
                    case -2:
                        snackbar = com.androidadvance.topsnackbar.TSnackbar.a(contentView, message, LENGTH_INDEFINITE);
                        break;
                }
                View snackbarview = null;
                if (snackbar != null) {
                    snackbarview = snackbar.getView();
                    snackbarview.setBackgroundColor(mActivity.getResources().getColor(mActivity.getResources().getIdentifier("background_floating_material_light", "color", "com.tellm.android.app")));
                    TextView snackbarTextView = (TextView) snackbarview.findViewById(mActivity.getResources().getIdentifier("snackbar_text", "id", "com.tellm.android.app"));
                    snackbarTextView.setTextColor(Color.BLACK);
                    snackbarTextView.setGravity(Gravity.CENTER);
//                XposedHelpers.callMethod(snackbar, "ay", LayoutHooks.JodelResIDs.ic_jx_icon, 256);
                    snackbar.show();
                } else throw new IllegalArgumentException("View is null!");

            } catch (Exception e) {
                Log.e(TSnackbar.class.getSimpleName(), "Could not create snackbar",e);
                Toast.makeText(mActivity, message, Toast.LENGTH_LONG).show();
            }

    }
}
