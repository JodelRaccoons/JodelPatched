package com.jodelapp.jodelandroidv3.view;

import android.app.Activity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.jodelapp.jodelandroidv3.jp.JPStorage;
import com.jodelapp.jodelandroidv3.jp.JPUtils;

/**
 * Created by Admin on 07.04.2018.
 */

class MainActivity$OnToggleLocationClickListener implements View.OnClickListener {

    private Activity mainActivity;
    private ImageView ivCrossingLine;
    private JPStorage mStorage;

    public MainActivity$OnToggleLocationClickListener(Activity mainActivity, ImageView ivCrossingLine, JPStorage mStorage) {
        this.mainActivity = mainActivity;
        this.ivCrossingLine = ivCrossingLine;
        this.mStorage = mStorage;
    }

    @Override
    public void onClick(View view) {
        if (ivCrossingLine.getVisibility() == View.VISIBLE) {
            fadeOutAndHideImage(ivCrossingLine);
            mStorage.setSpoofLocation(true);
        } else if (ivCrossingLine.getVisibility() == View.INVISIBLE) {
            fadeInAndShowImage(ivCrossingLine);
            mStorage.setSpoofLocation(false);
        }

        JPUtils.updateJodelLocation();
    }

    private void fadeOutAndHideImage(final ImageView img) {
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(125);

        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                img.setVisibility(View.INVISIBLE);
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }
        });

        img.startAnimation(fadeOut);
    }

    private void fadeInAndShowImage(final ImageView img) {
        Animation fadeOut = new AlphaAnimation(0, 1);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(125);

        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            public void onAnimationEnd(Animation animation) {
                img.setVisibility(View.VISIBLE);
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationStart(Animation animation) {
            }
        });

        img.startAnimation(fadeOut);
    }
}
