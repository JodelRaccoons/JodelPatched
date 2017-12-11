package com.jodelapp.jodelandroidv3.view;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jodelapp.jodelandroidv3.events.NavigateBackEvent;
import com.jodelapp.jodelandroidv3.events.PictureTakenEvent;
import com.jodelapp.jodelandroidv3.features.create_text_post.CreateTextPostFragment;
import com.squareup.otto.Subscribe;

import lanchon.dexpatcher.annotation.DexAction;
import lanchon.dexpatcher.annotation.DexAdd;
import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexIgnore;
import lanchon.dexpatcher.annotation.DexWrap;

@DexEdit(defaultAction = DexAction.IGNORE, contentOnly = true)
public class PostCreationFragment {
 /*Removed unnecessary stuff*/

    @DexIgnore
    private CreateTextPostFragment mTextPostFragment;

    @DexAdd
    public static PostCreationFragment mInstance;

    @DexAdd
    public SwipeableViewPager getViewPager() {
        return viewPager;
    }

    @DexIgnore
    private SwipeableViewPager viewPager;

    @DexWrap
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View mRootView = onCreateView(layoutInflater, viewGroup, bundle);
        mTextPostFragment.setPostCreationFragment(this);
        mInstance = this;
        return mRootView;
    }

    @DexIgnore
    public void handle(PictureTakenEvent pictureTakenEvent) {}
}
