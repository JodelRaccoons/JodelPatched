package com.jodelapp.jodelandroidv3.features.create_photo_post;

import android.content.Intent;
import android.view.View;

import com.jodelapp.jodelandroidv3.view.PostCreationFragment;

/**
 * Created by Admin on 01.01.2018.
 */

public class OnGalleryClickListener implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        Intent photoPicker = new Intent(Intent.ACTION_PICK);
        photoPicker.setType("image/*");
        PostCreationFragment.mInstance.getTextPostFragment().startActivityForResult(photoPicker, 90);
    }
}
