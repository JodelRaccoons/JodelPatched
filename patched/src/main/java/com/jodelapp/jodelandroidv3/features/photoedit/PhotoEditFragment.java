package com.jodelapp.jodelandroidv3.features.photoedit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jodelapp.jodelandroidv3.view.JodelFragment;
import com.tellm.android.app.mod.R;

import lanchon.dexpatcher.annotation.DexAction;
import lanchon.dexpatcher.annotation.DexAdd;
import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexIgnore;
import lanchon.dexpatcher.annotation.DexWrap;

import static android.widget.ImageView.ScaleType.CENTER_CROP;
import static android.widget.ImageView.ScaleType.FIT_CENTER;
import static android.widget.ImageView.ScaleType.FIT_XY;

@DexEdit(contentOnly = true, defaultAction = DexAction.IGNORE)
public class PhotoEditFragment extends JodelFragment{
    @DexIgnore
    ImageView picPreview;

    @DexIgnore
    RelativeLayout imageContainer;

    protected PhotoEditFragment(String s) {
        super(s);
    }

    @DexWrap
    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View rootView = onCreateView(layoutInflater, viewGroup, bundle);
        rootView.findViewById(R.id.ibToggleScale).setOnClickListener(new ScaleListener(picPreview));
        return rootView;
    }
}

@DexAdd
class ScaleListener implements View.OnClickListener {
    private ImageView imageView;

    ScaleListener(ImageView imageView) {
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