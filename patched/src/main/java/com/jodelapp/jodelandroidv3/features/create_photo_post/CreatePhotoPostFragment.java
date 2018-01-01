package com.jodelapp.jodelandroidv3.features.create_photo_post;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.jodelapp.jodelandroidv3.view.JodelFragment;
import com.tellm.android.app.mod.R;

import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexIgnore;
import lanchon.dexpatcher.annotation.DexWrap;

@SuppressLint("ValidFragment")
@DexEdit(contentOnly = true)
public class CreatePhotoPostFragment extends JodelFragment {

    @DexIgnore
    CreatePhotoPostFragment(String s) {
        super(s);
    }

    @DexIgnore
    public void shutterTapped() {

    }

    @DexWrap
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        ViewGroup container = (ViewGroup) onCreateView(layoutInflater, viewGroup, bundle);
        Log.i("JodelPatched", "initCameraPreview");
        int count = container.getChildCount();
        Log.i("JodelPatched", "child count = " + count);
        for (int i = 0; i < count; i++) {
            View v = container.getChildAt(i);
            if (v instanceof RelativeLayout) {
                Log.i("JodelPatched", "RelativeLayout at count = " + i);
                View galleryButton = layoutInflater.inflate(R.layout.jp_camera_gallery_button, (ViewGroup) v, false);
                ((RelativeLayout) v).addView(galleryButton);
                galleryButton.setOnClickListener(new OnClickListener());
            }
        }
        return container;
    }

    private class OnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

        }
    }
}
