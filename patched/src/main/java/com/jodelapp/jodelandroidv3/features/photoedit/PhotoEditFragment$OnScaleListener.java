package com.jodelapp.jodelandroidv3.features.photoedit;

import android.view.View;
import android.widget.ImageView;

import lanchon.dexpatcher.annotation.DexAdd;

import static android.widget.ImageView.ScaleType.CENTER_CROP;
import static android.widget.ImageView.ScaleType.FIT_CENTER;
import static android.widget.ImageView.ScaleType.FIT_XY;

/**
 * Created by Admin on 12.12.2017.
 */
@DexAdd
public class PhotoEditFragment$OnScaleListener implements View.OnClickListener {
    private ImageView imageView;

    PhotoEditFragment$OnScaleListener(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    public void onClick(View v) {
        switch (imageView.getScaleType()) {
            case CENTER_CROP:
                imageView.setScaleType(FIT_CENTER);
                break;
            case FIT_CENTER:
                imageView.setScaleType(FIT_XY);
                break;
            case FIT_XY:
                imageView.setScaleType(CENTER_CROP);
                break;
        }
    }
}
