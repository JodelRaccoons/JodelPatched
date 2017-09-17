package com.jodelapp.jodelandroidv3.features.create_photo_post;

import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jodelapp.jodelandroidv3.features.create_photo_post.listeners.CameraShutterListener;
import com.jodelapp.jodelandroidv3.view.JodelFragment;

import hack.getid.R;
import lanchon.dexpatcher.annotation.DexAdd;
import lanchon.dexpatcher.annotation.DexEdit;
import lanchon.dexpatcher.annotation.DexIgnore;
import lanchon.dexpatcher.annotation.DexWrap;

import static android.app.Activity.RESULT_OK;


@DexEdit
public class CreatePhotoPostFragment extends JodelFragment implements CreatePhotoPostContract.View {

    @DexIgnore
    Camera.PictureCallback cameraShutterListener;

    @DexWrap
    @Override
    public android.view.View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        android.view.View view = onCreateView(layoutInflater, viewGroup, bundle);
        view.findViewById(R.id.gallery_button).setOnClickListener(new GalleryListener(this));
        return view;
    }

    @DexAdd
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        if (requestCode == 90 && resultCode == RESULT_OK) {
            Uri IMAGE_URI = imageReturnedIntent.getData();
            ((CameraShutterListener) cameraShutterListener).imageUriToInject = IMAGE_URI;
            ((CameraShutterListener) cameraShutterListener).parentContext = this.getContext();
            Toast.makeText(this.getContext(), "Now just take a photo", Toast.LENGTH_SHORT).show();
        }
    }

    @DexIgnore
    public CreatePhotoPostFragment() {
        super("");
    }

    @DexIgnore
    @Override
    public void goBack() {
    }

    @DexIgnore
    @Override
    public void setCameraPreviewWithFlashMode(String s) {

    }

    @DexIgnore
    @Override
    public void switchCamera() {

    }

    @DexIgnore
    @Override
    public void takeSnapshot() {

    }

    @DexIgnore
    @Override
    public void updateShutterButtonColor(int i) {

    }
}

@DexAdd
class GalleryListener implements android.view.View.OnClickListener {

    private CreatePhotoPostFragment fragment;

    GalleryListener(CreatePhotoPostFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void onClick(android.view.View v) {
        Log.i("Jodel", "Insert from gallery");
        Intent photoPicker = new Intent(Intent.ACTION_PICK);
        photoPicker.setType("image/*");
        this.fragment.startActivityForResult(photoPicker, 90);
    }

}