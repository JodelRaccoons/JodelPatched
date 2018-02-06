package com.jodelapp.jodelandroidv3.features.create_text_post;

import android.content.Intent;
import android.view.View;

import com.jodelapp.jodelandroidv3.view.PostCreationFragment;

/**
 * Created by Admin on 04.01.2018.
 */

public class CreateTextPostFragment$OnGalleryPickerClickListener implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        Intent photoPicker = new Intent(Intent.ACTION_PICK);
        photoPicker.setType("image/*");
        PostCreationFragment.mInstance.startActivityForResult(photoPicker, 90);
    }
}