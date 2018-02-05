package com.jodelapp.jodelandroidv3.features.create_photo_post;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.jodelapp.jodelandroidv3.features.create_text_post.CreateTextPostFragment;
import com.jodelapp.jodelandroidv3.jp.JPUtils;
import com.jodelapp.jodelandroidv3.view.MainActivity;
import com.jodelapp.jodelandroidv3.view.PostCreationFragment;

/**
 * ? For gallery upload
 */
public class OnGalleryClickListener implements View.OnClickListener {

    private PostCreationFragment mInstance;


    public OnGalleryClickListener(PostCreationFragment mInstance) {
        this.mInstance = mInstance;
    }

    @Override
    public void onClick(View view) {
        if (mInstance != null) {
            if (mInstance.isAdded()) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                mInstance.startActivityForResult(photoPicker, 90);
            } else {
                Log.d(getClass().getSimpleName(),"Fragment was not added to activity");
            }
        } else {
            Log.d(getClass().getSimpleName(),"Fragment was null");
        }
    }
}
