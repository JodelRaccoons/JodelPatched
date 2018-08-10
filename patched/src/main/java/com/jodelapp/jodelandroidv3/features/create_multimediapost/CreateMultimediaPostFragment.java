package com.jodelapp.jodelandroidv3.features.create_multimediapost;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.jodelapp.jodelandroidv3.analytics.state.EntryPoint;
import com.jodelapp.jodelandroidv3.features.create_photo_post.OnGalleryClickListener;
import com.jodelapp.jodelandroidv3.view.JodelFragment;
import com.jodelapp.jodelandroidv3.view.PostCreationFragment;
import com.tellm.android.app.mod.R;

import java.util.Objects;

import lanchon.dexpatcher.annotation.DexAction;
import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexWrap;

@DexEdit(contentOnly = true, defaultAction = DexAction.IGNORE)
public class CreateMultimediaPostFragment extends JodelFragment {
    protected CreateMultimediaPostFragment(EntryPoint entryPoint) {
        super(entryPoint);
    }

    @DexWrap
    @Nullable
    @Override
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        ViewGroup container = (ViewGroup) onCreateView(layoutInflater, viewGroup, bundle);
        Log.i("JodelPatched", "initCameraPreview");
        int count = Objects.requireNonNull(container).getChildCount();
        Log.i("JodelPatched", "child count = " + count);
        for (int i = 0; i < count; i++) {
            View v = container.getChildAt(i);
            if (v instanceof RelativeLayout) {
                Log.i("JodelPatched", "RelativeLayout at count = " + i);
                View galleryButton = layoutInflater.inflate(R.layout.jp_camera_gallery_button, (ViewGroup) v, false);
                ((RelativeLayout) v).addView(galleryButton);
                galleryButton.setOnClickListener(new OnGalleryClickListener(PostCreationFragment.mInstance));
            }
        }
        return container;
    }
}
