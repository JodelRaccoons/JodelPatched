package com.jodelapp.jodelandroidv3.features.create_photo_post;

import android.content.Intent;
import android.view.View;

import com.jodelapp.jodelandroidv3.features.create_text_post.CreateTextPostFragment;
import com.jodelapp.jodelandroidv3.view.PostCreationFragment;

/**
 * ? For gallery upload
 */
public class OnGalleryClickListener implements View.OnClickListener {

    private CreateTextPostFragment mInstance;


    public OnGalleryClickListener(CreateTextPostFragment mInstance) {
        this.mInstance = mInstance;
    }

    @Override
    public void onClick(View view) {
        Intent photoPicker = new Intent(Intent.ACTION_PICK);
        photoPicker.setType("image/*");
        mInstance.startActivityForResult(photoPicker, 90);
    }
}
