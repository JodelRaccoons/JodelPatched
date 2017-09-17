package com.jodelapp.jodelandroidv3.features.create_photo_post.listeners;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.util.Log;

import com.jodelapp.jodelandroidv3.AppModule;
import com.jodelapp.jodelandroidv3.events.PictureTakenEvent;
import com.squareup.otto.Bus;

import java.io.FileNotFoundException;
import java.io.InputStream;

import lanchon.dexpatcher.annotation.DexAction;
import lanchon.dexpatcher.annotation.DexAdd;
import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexIgnore;
import lanchon.dexpatcher.annotation.DexWrap;

@DexEdit(staticConstructorAction = DexAction.ADD)
public class CameraShutterListener implements Camera.PictureCallback {
    @DexAdd
    public Uri imageUriToInject = null;
    @DexAdd
    public Context parentContext = null;

    @DexIgnore
    private Bus bus;

    @DexIgnore
    CameraShutterListener() {
    }

    static {
    }

    @DexWrap
    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        if (imageUriToInject == null) {
            onPictureTaken(data, camera);
        } else {
            Log.i("Jodel", "Found URI to inject");
            try {
                InputStream image_stream = parentContext.getContentResolver().openInputStream(imageUriToInject);
                Bitmap imageToInject = BitmapFactory.decodeStream(image_stream);
                camera.startPreview();
                this.bus.post(new PictureTakenEvent(imageToInject));
            } catch (FileNotFoundException e) {
                Log.e("Jodel", "FileNotFound from gallery");
            }

        }
    }
}
