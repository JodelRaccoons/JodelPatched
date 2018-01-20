package com.jodelapp.jodelandroidv3.features.create_text_post;

import android.content.Intent;
import android.view.View;

/**
 * Created by Admin on 04.01.2018.
 */

public class CreateTextPostFragment$OnGalleryPickerClickListener implements View.OnClickListener {

    private CreateTextPostFragment mInstance;

    public CreateTextPostFragment$OnGalleryPickerClickListener(CreateTextPostFragment mInstance) {
        this.mInstance = mInstance;
    }

    @Override
    public void onClick(View v) {
        Intent photoPicker = new Intent(Intent.ACTION_PICK);
        photoPicker.setType("image/*");
        mInstance.startActivityForResult(photoPicker, 90);
    }
}