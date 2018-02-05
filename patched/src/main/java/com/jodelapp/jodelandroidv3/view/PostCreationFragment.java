package com.jodelapp.jodelandroidv3.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jodelapp.jodelandroidv3.events.PictureTakenEvent;
import com.jodelapp.jodelandroidv3.features.create_text_post.CreateTextPostFragment;
import com.jodelapp.jodelandroidv3.jp.TSnackbar;

import lanchon.dexpatcher.annotation.DexAction;
import lanchon.dexpatcher.annotation.DexAdd;
import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexIgnore;
import lanchon.dexpatcher.annotation.DexWrap;

import static android.app.Activity.RESULT_OK;

/**
 * ? For gallery upload
 */
@DexEdit(defaultAction = DexAction.IGNORE, contentOnly = true)
public class PostCreationFragment extends JodelFragment{

    @DexAdd
    public static PostCreationFragment mInstance;

    protected PostCreationFragment(String s) {
        super(s);
    }

    @DexWrap
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View mRootView = onCreateView(layoutInflater, viewGroup, bundle);
        mInstance = this;
        return mRootView;
    }

    @DexIgnore
    public void handle(PictureTakenEvent pictureTakenEvent) {}

    @DexAdd
    @Override
    public void onActivityResult(int i, int i1, Intent intent) {
        if (i == 90 && i1 == RESULT_OK) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), intent.getData());
                PostCreationFragment.mInstance.handle(new PictureTakenEvent(bitmap));
            } catch (Exception e) {
                TSnackbar.make("Error while picking image: "+e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
