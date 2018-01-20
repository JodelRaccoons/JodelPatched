package com.jodelapp.jodelandroidv3.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jodelapp.jodelandroidv3.events.PictureTakenEvent;
import com.jodelapp.jodelandroidv3.features.create_text_post.CreateTextPostFragment;

import lanchon.dexpatcher.annotation.DexAction;
import lanchon.dexpatcher.annotation.DexAdd;
import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexIgnore;
import lanchon.dexpatcher.annotation.DexWrap;

/**
 * ? For gallery upload
 */
@DexEdit(defaultAction = DexAction.IGNORE, contentOnly = true)
public class PostCreationFragment {

    @DexAdd
    public static PostCreationFragment mInstance;

    @DexWrap
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View mRootView = onCreateView(layoutInflater, viewGroup, bundle);
        mInstance = this;
        return mRootView;
    }

    @DexIgnore
    public void handle(PictureTakenEvent pictureTakenEvent) {
    }
}
