package com.jodelapp.jodelandroidv3.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import com.jodelapp.jodelandroidv3.AppModule;
import com.jodelapp.jodelandroidv3.events.PictureTakenEvent;

import java.io.FileNotFoundException;
import java.io.InputStream;

import lanchon.dexpatcher.annotation.DexAdd;
import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexIgnore;

import static android.app.Activity.RESULT_OK;

@SuppressLint("ValidFragment")
@DexEdit
public class PostCreationFragment extends JodelFragment {
    @DexIgnore
    public PostCreationFragment(String s) {
        super(s);
    }

    @DexAdd
    private Bitmap hasSelected = null;

    @DexAdd
    public void onStart() {
        source_onStart();
        if (hasSelected == null) {
            Intent photoPicker = new Intent(Intent.ACTION_PICK);
            photoPicker.setType("image/*");
            startActivityForResult(photoPicker, 90);
        } else {
            PictureTakenEvent event = new PictureTakenEvent(hasSelected);
            AppModule.staticBus.post(event);
        }
    }

    @DexEdit(target = "onStart")
    public void source_onStart() {
    }

    @DexAdd
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        if (requestCode == 90 && resultCode == RESULT_OK) {
            Uri IMAGE_URI = imageReturnedIntent.getData();
            try {
                InputStream image_stream = this.getContext().getContentResolver().openInputStream(IMAGE_URI);
                hasSelected = BitmapFactory.decodeStream(image_stream);
            } catch (FileNotFoundException e) {
                Log.e("Jodel", "FileNotFound from gallery");
            }
        }
    }
}